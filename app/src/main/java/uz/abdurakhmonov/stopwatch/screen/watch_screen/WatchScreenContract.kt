package uz.abdurakhmonov.stopwatch.screen.watch_screen

import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.Flow
import uz.abdurakhmonov.domain.remote.History
import uz.abdurakhmonov.stopwatch.utils.Animations
import uz.abdurakhmonov.stopwatch.utils.BtnState
import uz.abdurakhmonov.stopwatch.utils.Orientation

interface WatchScreenContract{

    val uiState: Flow<UiState>
//    val stateBtn: Flow<BtnState>
//    val stateStart: Flow<Boolean>
//    val stateStopWatch: Flow<String>
//    val stateFlags: SharedFlow<List<History>>
//
//    val animationText: Flow<Animations>
//    val screenOrientation:Flow<Orientation>
//    val screenState:Flow<Boolean>

    fun intent(intent: Intent)


    data class UiState(
        var stateStart: Boolean = true,
        var stateStopWatch: String = "00:00:00",
        var stateBtn: BtnState = BtnState.START,
        var stateFlags: MutableList<History> = mutableListOf(),
        var animationText: Animations = Animations(64.dp, 0.9f, 0.1f),
        var screenOrientation: Orientation = Orientation.POR,
        var screenState: Boolean = true
    )




    sealed interface Intent{
        data object ClickStart: Intent
        data object ClickRight:Intent
        data object ClickLeft:Intent
        data class UpdateStart(val check:Boolean):Intent
        data object ClickFlag:Intent
        data class ScreenOrientation(val orientation: Orientation):Intent
        data object OnStart:Intent
        data object OnStop:Intent
        data object OnDestroy:Intent
    }

//    fun clickStart()
//    fun clickRight()
//    fun clickLeft()
//    fun updateStart(check:Boolean)
//    fun clickFlag()
//    fun screenOrientation(orientation: Orientation)
//
//    fun onStart()
//    fun onStop()
//    fun onDestroy()

}