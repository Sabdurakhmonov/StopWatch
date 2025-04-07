package uz.abdurakhmonov.stopwatch.screen.watch_screen

import android.content.res.Configuration
import android.media.MediaPlayer
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
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
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import uz.abdurakhmonov.stopwatch.R
import uz.abdurakhmonov.stopwatch.ui.theme.fontFamily
import uz.abdurakhmonov.stopwatch.ui.theme.primary
import uz.abdurakhmonov.stopwatch.ui.theme.secondary
import uz.abdurakhmonov.stopwatch.utils.AnimatedButton
import uz.abdurakhmonov.stopwatch.utils.ItemFlags
import uz.abdurakhmonov.stopwatch.utils.Orientation
import uz.abdurakhmonov.stopwatch.utils.dpToSp
import uz.abdurakhmonov.stopwatch.utils.playMusic
import uz.abdurakhmonov.stopwatch.utils.state

@Composable
fun StopWatchScreen(
    //viewModel: HomeScreenVM,
    intent: (WatchScreenContract.Intent) -> Unit,
    state: State<WatchScreenContract.UiState>,
) {

    val configuration = LocalConfiguration.current
    val context = LocalContext.current
    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }


    when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            intent(WatchScreenContract.Intent.ScreenOrientation(Orientation.LAN))
        }

        else -> intent(WatchScreenContract.Intent.ScreenOrientation(Orientation.POR))
    }

    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_STOP -> {
                    intent(WatchScreenContract.Intent.OnStop)
                }

                Lifecycle.Event.ON_DESTROY -> {
                    intent(WatchScreenContract.Intent.OnDestroy)
                }

                else -> {}
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

//    val screenState by state.value.collectAsState(initial = true)
//    val stateFlags by state.collectAsState()
//    val itemFlag = remember {
//        mutableStateListOf(stateFlags)
//    }
//
//    val timer bystateStopWatch.collectAsState(initial = "00:00:00")
//    val stateBtn bystateBtn.collectAsState(initial = BtnState.START)
//
//    val clickBtn bystateStart.collectAsState(initial = true)
//    val anime byanimationText.collectAsState(Animations(64.dp, 0.9f, 0.1f))

    val animatedSize by animateFloatAsState(
        targetValue = state.value.animationText.watchSize,
        label = "watchSize"
    )
    val animatedTextSize by animateDpAsState(
        targetValue = state.value.animationText.textSize,
        label = "textSize"
    )
    val animatedItems by animateFloatAsState(
        targetValue = state.value.animationText.itemsSize,
        label = "itemsSize"
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = secondary),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            modifier = Modifier.weight(weight = if (state.value.screenState) animatedSize else 0.4f),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier
                    .padding(bottom = 16.dp),
                textAlign = TextAlign.Center,
                text = state.value.stateStopWatch,
                color = primary,
                fontSize = if (state.value.screenState) animatedTextSize.dpToSp() else 40.sp,
                fontFamily = fontFamily,
                fontWeight = FontWeight.Medium,
            )
        }

        if (state.value.stateFlags.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier
                    .padding(bottom = 32.dp)
                    .weight(weight = if (state.value.screenState) animatedItems else 0.3f),
                contentPadding = PaddingValues(bottom = 8.dp)
            ) {
                items(items = state.value.stateFlags.reversed()) { items ->
                    ItemFlags(
                        count = items.id,
                        timeOld = items.oldTime,
                        timeNew = items.time
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            if (state.value.stateStart) {
                AnimatedButton(
                    modifier = Modifier
                        .width(110.dp)
                        .height(56.dp),
                    icon = painterResource(id = R.drawable.ic_play)
                ) {
                    intent(WatchScreenContract.Intent.ClickStart)
                    playMusic(context, mediaPlayer)
                }
            } else {
                AnimatedButton(
                    modifier = Modifier
                        .size(60.dp),
                    contentPadding = PaddingValues(0.dp),
                    shape = CircleShape,
                    icon = state(state = state.value.stateBtn, 0)
                ) {
                    intent(WatchScreenContract.Intent.ClickLeft)
                    playMusic(context, mediaPlayer)
                }
                AnimatedButton(
                    modifier = Modifier
                        .size(60.dp),
                    contentPadding = PaddingValues(0.dp),
                    shape = CircleShape,
                    icon = state(state = state.value.stateBtn, 1)
                ) {
                    intent(WatchScreenContract.Intent.ClickRight)
                    playMusic(context, mediaPlayer)
                }
            }
        }
    }

}




