package uz.abdurakhmonov.stopwatch.screen.home_screen

import android.util.Log
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.abdurakhmonov.domain.remote.History
import uz.abdurakhmonov.domain.use_case.LocalDateStoreUC
import uz.abdurakhmonov.stopwatch.utils.Animations
import uz.abdurakhmonov.stopwatch.utils.BtnState
import uz.abdurakhmonov.stopwatch.utils.Orientation
import uz.abdurakhmonov.stopwatch.utils.toTime
import javax.inject.Inject

@HiltViewModel
class HomeScreenVMImpl @Inject constructor(
    private val useCase: LocalDateStoreUC
) : HomeScreenVM, ViewModel() {

    override var stateStart = MutableStateFlow(true)
    override val stateStopWatch = MutableStateFlow("00:00:00")
    override val stateFlags = MutableSharedFlow<List<History>>(replay = 1, onBufferOverflow = BufferOverflow.DROP_LATEST)
    override val stateBtn = MutableStateFlow(BtnState.START)
    override val animationText = MutableStateFlow(Animations(64.dp, 0.9f, 0.1f))
    override val screenOrientation = MutableStateFlow(Orientation.POR)
    override val screenState = MutableStateFlow(true)
    private var job: Job? = null

    private val process = MutableStateFlow(false)
    private var counterFlag = 0

    private val flags = mutableListOf<History>()

    private var timer = 0L
    private var lastTime = 0L

    override fun clickStart() {
        stateStart.value = false
        stateBtn.value = BtnState.START
        process.value = true
        start()
    }

    init {
        //onStart()
    }

    override fun clickRight() {
        when (stateBtn.value) {
            BtnState.START -> {
                stateBtn.value = BtnState.PAUSE
                process.value = false
            }

            else -> {
                stateBtn.value = BtnState.START
                process.value = true
                start()
            }
        }
    }

    override fun clickLeft() {
        when (stateBtn.value) {
            BtnState.PAUSE -> {
                clear()
            }

            else -> {
                animationText.value = Animations(40.dp, 0.1f, 0.9f)
                clickFlag()
            }
        }
    }


    override fun updateStart(check: Boolean) {
        stateStart.value = check
    }

    override fun clickFlag() {
        flags.add(
            History(
                ++counterFlag,
                oldTime = (timer - lastTime).toTime(),
                time = timer.toTime()
            )
        )
        lastTime = timer
        val newList = mutableListOf<History>()
        newList.addAll(flags.toList())
        viewModelScope.launch {
            stateFlags.emit(newList)
        }
    }

    override fun screenOrientation(orientation: Orientation) {
        when (orientation) {
            Orientation.POR -> {
                screenState.value = true
            }

            Orientation.LAN -> {
                screenState.value = false
            }
        }
        screenOrientation.value = orientation
    }

    override fun onStart() {
        viewModelScope.launch {

            counterFlag = useCase.getCounter()

            val state = useCase.getState()
            when (state) {
                "START" -> {
                    Log.d("AAA", "onStart: onStart")
                    stateBtn.value = BtnState.START
                    if (useCase.getDate() > 0L) {
                        timer = System.currentTimeMillis() - useCase.getDate() + useCase.getTimer()
                    }
                    flags.clear()
                    stateStopWatch.value = timer.toTime()
                    useCase.getFlags().onEach {
                        if (it.isNotEmpty()) {
                            animationText.value = Animations(40.dp, 0.1f, 0.9f)
                        }
                        flags.addAll(it)
                    }.launchIn(viewModelScope)
                    stateFlags.emit(flags.toList())
                    stateStart.value = false
                    process.value = true
                    start()
                }

                "PAUSE" -> {
                    useCase.getFlags().onEach {
                        if (it.isNotEmpty()) {
                            animationText.value = Animations(40.dp, 0.1f, 0.9f)
                        }
                        flags.clear()
                        flags.addAll(it)
                        stateFlags.emit(flags)
                    }.launchIn(viewModelScope)

                    stateBtn.value = BtnState.PAUSE
                    timer = useCase.getTimer()

                    stateStopWatch.value = timer.toTime()
                    stateStart.value = false
                }

                else -> {
                    clear()
                }
            }
        }
    }

    override fun onStop() {
        when (stateBtn.value) {
            BtnState.START -> {
                viewModelScope.launch {
                    Log.d("AAA", "onStop: START")
                    useCase.setState(stateBtn.value.name)
                    useCase.setTimer(timer)
                    useCase.setDate(System.currentTimeMillis())
                    useCase.setFlags(flags)
                    stateBtn.value = BtnState.START
                    useCase.setCounter(counterFlag)
                }
            }

            BtnState.STOP -> {
                viewModelScope.launch {
                    useCase.setState("STOP")
                }
                clear()
            }

            BtnState.PAUSE -> {
                viewModelScope.launch {
                    useCase.setState(stateBtn.value.name)
                    useCase.setTimer(timer)
                    useCase.setDate(System.currentTimeMillis())
                    useCase.setFlags(flags)
                    stateBtn.value = BtnState.PAUSE
                    useCase.setCounter(counterFlag)
                }
            }

            BtnState.FLAG -> {}
        }
    }

    override fun onDestroy() {
        when (stateBtn.value) {
            BtnState.START -> {
                Log.d("AAA", "onDestroy: Start")
                viewModelScope.launch {
                    useCase.setState(stateBtn.value.name)
                    useCase.setTimer(timer)
                    useCase.setDate(System.currentTimeMillis())
                    useCase.setFlags(flags)
                    stateBtn.value = BtnState.START
                    useCase.setCounter(counterFlag)
                }
            }

            BtnState.STOP -> {
                viewModelScope.launch {
                    useCase.setState("STOP")
                }
                clear()
            }

            BtnState.PAUSE -> {
                Log.d("AAA", "onDestroy: PAUSE")
                viewModelScope.launch {
                    useCase.setState(stateBtn.value.name)
                    useCase.setTimer(timer)
                    useCase.setDate(System.currentTimeMillis())
                    useCase.setFlags(flags)
                    stateBtn.value = BtnState.PAUSE
                    useCase.setCounter(counterFlag)
                }
            }

            BtnState.FLAG -> {}
        }
    }


    private fun start() {
        viewModelScope.launch(Dispatchers.Unconfined) {
            while (process.value) {
                delay(10)
                timer += 10
                stateStopWatch.value = timer.toTime()
            }
        }
    }


    private fun clear() {
        animationText.value = Animations(64.dp, 0.9f, 0.1f)
        stateBtn.value = BtnState.STOP
        stateStart.value = true
        timer = 0
        counterFlag = 0
        lastTime = 0
        viewModelScope.launch {
            stateFlags.emit(emptyList())
        }
        flags.clear()
        stateStopWatch.value = "00:00:00"
        process.value = false
    }
}