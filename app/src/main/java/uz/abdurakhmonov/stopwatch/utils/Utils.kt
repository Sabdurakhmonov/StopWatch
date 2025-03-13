package uz.abdurakhmonov.stopwatch.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import uz.abdurakhmonov.stopwatch.R
import java.util.concurrent.TimeUnit

@Composable
fun Dp.dpToSp(): TextUnit {
    val density = LocalDensity.current
    return with(density) { toSp() }
}

enum class BtnState{
    START,
    STOP,
    PAUSE,
    FLAG
}

enum class Orientation{
    POR,
    LAN
}

data class Animations(
    val textSize: Dp,
    val watchSize: Float,
    val itemsSize: Float
)




fun Long.toTime(): String {
    val minutes = TimeUnit.MILLISECONDS.toMinutes(this)
    val seconds = TimeUnit.MILLISECONDS.toSeconds(this) % 60
    val milliseconds = this % 1000/10
    return String.format("%02d:%02d:%02d", minutes, seconds, milliseconds)
}

fun Int.toTime(): String {
    val h = this / 3600
    val m = (this % 3600) / 60
    val s = this % 60
    return String.format("%02d:%02d:%02d", h, m, s)
}

@Composable
fun state(state: BtnState, index: Int): Painter {
    if (index == 0) {
        return when (state) {
            BtnState.START -> {
                painterResource(id = R.drawable.ic_flag)
            }

            BtnState.STOP -> {
                painterResource(id = R.drawable.ic_flag)
            }

            BtnState.PAUSE -> {
                painterResource(id = R.drawable.ic_stop)
            }

            BtnState.FLAG -> {
                painterResource(id = R.drawable.ic_flag)

            }
        }
    }else{
        return when (state) {
            BtnState.START -> {
                painterResource(id = R.drawable.ic_pause)
            }

            BtnState.STOP -> {
                painterResource(id = R.drawable.ic_pause)
            }

            BtnState.PAUSE -> {
                painterResource(id = R.drawable.ic_play)
            }

            BtnState.FLAG -> {
                painterResource(id = R.drawable.ic_pause)
            }
        }
    }
}