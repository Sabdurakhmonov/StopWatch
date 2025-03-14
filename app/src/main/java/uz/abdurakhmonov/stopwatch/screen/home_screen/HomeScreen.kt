package uz.abdurakhmonov.stopwatch.screen.home_screen

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.media.MediaPlayer
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.abdurakhmonov.domain.remote.History
import uz.abdurakhmonov.stopwatch.R
import uz.abdurakhmonov.stopwatch.ui.theme.fontFamily
import uz.abdurakhmonov.stopwatch.ui.theme.primary
import uz.abdurakhmonov.stopwatch.ui.theme.secondary
import uz.abdurakhmonov.stopwatch.utils.AnimatedButton
import uz.abdurakhmonov.stopwatch.utils.Animations
import uz.abdurakhmonov.stopwatch.utils.BtnState
import uz.abdurakhmonov.stopwatch.utils.ItemAppbar
import uz.abdurakhmonov.stopwatch.utils.ItemFlags
import uz.abdurakhmonov.stopwatch.utils.Orientation
import uz.abdurakhmonov.stopwatch.utils.dpToSp
import uz.abdurakhmonov.stopwatch.utils.state

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun HomeScreenContent(
    viewModel: HomeScreenVM,
) {
    val scope = rememberCoroutineScope()
    val configuration = LocalConfiguration.current
    val context = LocalContext.current
    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }

    when(configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            viewModel.screenOrientation(Orientation.LAN)
        }
        else -> viewModel.screenOrientation(Orientation.POR)
    }

    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_STOP->{
                    viewModel.onStop()
                }
                Lifecycle.Event.ON_DESTROY->{
                    viewModel.onDestroy()
                }
                else->{}
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    val screenState by viewModel.screenState.collectAsState(initial = true)
    var itemFlags = mutableListOf<History>()
    viewModel.stateFlags.onEach {
        itemFlags = it.toMutableList()
    }.launchIn(scope)

    val timer by viewModel.stateStopWatch.collectAsState(initial = "00:00:00")
    val stateBtn by viewModel.stateBtn.collectAsState(initial = BtnState.START)

    val clickBtn by viewModel.stateStart.collectAsState(initial = true)
    val anime by viewModel.animationText.collectAsState(Animations(64.dp,0.9f,0.1f))

    val animatedSize by animateFloatAsState(targetValue = anime.watchSize, label = "watchSize")
    val animatedTextSize by animateDpAsState(targetValue = anime.textSize, label = "textSize")
    val animatedItems by animateFloatAsState(targetValue = anime.itemsSize, label = "itemsSize")

    Scaffold(
        containerColor = secondary,
        topBar = {
            ItemAppbar(
                title = "StopWatch",
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column(
                modifier = Modifier.weight(weight = if(screenState) animatedSize else 0.4f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier
                        .padding(bottom = 16.dp),
                    textAlign = TextAlign.Center,
                    text = timer,
                    color = primary,
                    fontSize = if(screenState) animatedTextSize.dpToSp() else 40.sp,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Medium,
                )
            }

            if(itemFlags.isNotEmpty()){
                LazyColumn(
                    modifier = Modifier
                        .padding(bottom = 32.dp)
                        .weight(weight = if (screenState) animatedItems else 0.3f),
                    contentPadding = PaddingValues(bottom = 8.dp)
                ) {
                    items(items = itemFlags.reversed()) { itemFlags ->
                        ItemFlags(count = itemFlags.id, timeOld = itemFlags.oldTime, timeNew = itemFlags.time)
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                if (clickBtn) {
                    AnimatedButton(
                        modifier = Modifier
                            .width(110.dp)
                            .height(56.dp),
                        icon = painterResource(id = R.drawable.ic_play)
                    ) {
                        viewModel.clickStart()
                        mediaPlayer?.release()
                        mediaPlayer = MediaPlayer.create(context, R.raw.music).apply {
                            start()
                        }
                    }
                } else {
                    AnimatedButton(
                        modifier = Modifier
                            .size(60.dp),
                        contentPadding = PaddingValues(0.dp),
                        shape = CircleShape,
                        icon = state(state = stateBtn, 0)
                    ) {
                        viewModel.clickLeft()
                        mediaPlayer?.release()
                        mediaPlayer = MediaPlayer.create(context, R.raw.music).apply {
                            start()
                        }
                    }
                    AnimatedButton(
                        modifier = Modifier
                            .size(60.dp),
                        contentPadding = PaddingValues(0.dp),
                        shape = CircleShape,
                        icon = state(state = stateBtn, 1)
                    ) {
                        viewModel.clickRight()
                        mediaPlayer?.release()
                        mediaPlayer = MediaPlayer.create(context, R.raw.music).apply {
                            start()
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
   // HomeScreenContent()
}



