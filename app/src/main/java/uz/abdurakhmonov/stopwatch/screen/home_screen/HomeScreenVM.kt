package uz.abdurakhmonov.stopwatch.screen.home_screen

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import uz.abdurakhmonov.domain.remote.History
import uz.abdurakhmonov.stopwatch.utils.Animations
import uz.abdurakhmonov.stopwatch.utils.BtnState
import uz.abdurakhmonov.stopwatch.utils.Orientation

interface HomeScreenVM{

    val stateStart : Flow<Boolean>
    val stateStopWatch: Flow<String>
    val stateFlags: SharedFlow<List<History>>
    val stateBtn: Flow<BtnState>
    val animationText: Flow<Animations>
    val screenOrientation:Flow<Orientation>
    val screenState:Flow<Boolean>


    fun clickStart()
    fun clickRight()
    fun clickLeft()
    fun updateStart(check:Boolean)
    fun clickFlag()
    fun screenOrientation(orientation: Orientation)

    fun onStart()
    fun onStop()
    fun onDestroy()

}