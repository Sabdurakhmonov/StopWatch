package uz.abdurakhmonov.stopwatch.utils

import android.graphics.drawable.Icon
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.abdurakhmonov.stopwatch.ui.theme.btnColor
import uz.abdurakhmonov.stopwatch.ui.theme.btnContentColor
import uz.abdurakhmonov.stopwatch.ui.theme.fontFamily
import uz.abdurakhmonov.stopwatch.ui.theme.primary
import uz.abdurakhmonov.stopwatch.ui.theme.primary1
import uz.abdurakhmonov.stopwatch.ui.theme.secondary


@Composable
fun ItemFlags(
    flags: List<String> = listOf(),
    count: Int = 1,
    timeOld: String = "00:00:00",
    timeNew: String = "00:00:00"
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Text(
            text = "$count",
            color = primary1,
            fontSize = 18.sp,
            fontFamily = fontFamily,
        )
        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = "+ $timeOld",
            color = primary1,
            fontSize = 18.sp,
            fontFamily = fontFamily,
        )
        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = timeNew,
            color = primary,
            fontSize = 20.sp,
            fontFamily = fontFamily,
            fontWeight = FontWeight.Medium
        )
    }
    Text(
        text = flags.joinToString(", "),
        color = primary,
        fontSize = 16.sp,
        fontFamily = fontFamily,
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemAppbar(
    title: String,
    startIcon: Icon? = null,
    actionIcon: Icon? = null
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = secondary,
        ),
        title = {
            Text(
                text = title,
                fontFamily = fontFamily,
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                color = primary
            )
        }
    )
}

@Composable
fun AnimatedButton(
    modifier: Modifier = Modifier,
    shape: Shape = ButtonDefaults.shape,
    contentPadding:PaddingValues = ButtonDefaults.ContentPadding,
    icon: Painter,
    onClick: () -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.85f else 1f,
        animationSpec = tween(durationMillis = 200), label = ""
    )
    val coroutine = rememberCoroutineScope()

    Button(
        contentPadding = contentPadding,
        onClick = {
            onClick.invoke()
            isPressed = !isPressed
            coroutine.launch(Dispatchers.Unconfined) {
                delay(180)
                isPressed = !isPressed
                cancel()
            }
        },
        shape = shape,
        colors = ButtonDefaults.buttonColors(
            containerColor = btnColor,
            contentColor = btnContentColor
        ),
        modifier = modifier
            .graphicsLayer(scaleX = scale, scaleY = scale)
    ) {
        Icon(painter = icon, contentDescription = "icons")
    }
}