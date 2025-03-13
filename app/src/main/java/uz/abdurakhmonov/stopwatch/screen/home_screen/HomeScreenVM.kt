package uz.abdurakhmonov.stopwatch.screen.home_screen

import kotlinx.coroutines.flow.Flow
import uz.abdurakhmonov.domain.remote.History
import uz.abdurakhmonov.stopwatch.utils.Animations
import uz.abdurakhmonov.stopwatch.utils.BtnState
import uz.abdurakhmonov.stopwatch.utils.Orientation

interface HomeScreenVM{

    val stateStart : Flow<Boolean>
    val stateStopWatch: Flow<String>
    val stateFlags: Flow<List<History>>
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
    fun onResume()
    fun onPause()
    fun onStop()
    fun onDestroy()

}