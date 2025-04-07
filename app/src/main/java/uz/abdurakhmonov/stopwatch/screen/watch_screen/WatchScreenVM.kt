package uz.abdurakhmonov.stopwatch.screen.watch_screen

import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.abdurakhmonov.domain.remote.History
import uz.abdurakhmonov.domain.use_case.stop_watch.LocalDateStoreUC
import uz.abdurakhmonov.stopwatch.screen.watch_screen.WatchScreenContract.*
import uz.abdurakhmonov.stopwatch.utils.Animations
import uz.abdurakhmonov.stopwatch.utils.BtnState
import uz.abdurakhmonov.stopwatch.utils.Orientation
import uz.abdurakhmonov.stopwatch.utils.toTime
import javax.inject.Inject

@HiltViewModel
class WatchScreenVM @Inject constructor(
    private val useCase: LocalDateStoreUC
) : WatchScreenContract, ViewModel() {


//    override var stateStart = MutableStateFlow(true)
//    override val stateStopWatch = MutableStateFlow("00:00:00")
//    override val stateFlags =
//        MutableSharedFlow<List<History>>(replay = 1, onBufferOverflow = BufferOverflow.DROP_LATEST)
//    override val stateBtn = MutableStateFlow(BtnState.START)
//    override val animationText = MutableStateFlow(Animations(64.dp, 0.9f, 0.1f))
//    override val screenOrientation = MutableStateFlow(Orientation.POR)
//    override val screenState = MutableStateFlow(true)

    private var stateBtn = BtnState.NONE
    private val process = MutableStateFlow(false)
    private var counterFlag = 0
    private val flags = mutableListOf<History>()
    private var timer = 0L
    private var lastTime = 0L

    init {
        onStart()
    }

    override val uiState = MutableStateFlow(UiState())


    override fun intent(intent: Intent) {
        when (intent) {
            Intent.ClickFlag -> clickFlag()
            Intent.ClickLeft -> clickLeft()
            Intent.ClickRight -> clickRight()
            Intent.ClickStart -> clickStart()
            Intent.OnDestroy -> onDestroy()
            Intent.OnStart -> {}
            Intent.OnStop -> onStop()
            is Intent.ScreenOrientation -> screenOrientation(intent.orientation)
            is Intent.UpdateStart -> updateStart(intent.check)
        }
    }

    private fun reduce(state: UiState.() -> UiState) {
        uiState.value = state(uiState.value)
    }

    private fun clickStart() {
        reduce {
            copy(
                stateBtn = BtnState.START,
                stateStart = false
            )
        }
        stateBtn = BtnState.START
        process.value = true
        start()
    }

    private fun clickRight() {
        when (stateBtn) {
            BtnState.START -> {
                stateBtn = BtnState.PAUSE
                reduce {
                    copy(
                        stateBtn = BtnState.PAUSE
                    )
                }
                process.value = false
            }

            else -> {
                reduce {
                    copy(
                        stateBtn = BtnState.START
                    )
                }
                stateBtn = BtnState.START
                process.value = true
                start()
            }
        }
    }

    private fun clickLeft() {
        when (stateBtn) {
            BtnState.PAUSE -> {
                clear()
            }

            else -> {
                reduce {
                    copy(
                        animationText = Animations(40.dp, 0.1f, 0.9f)
                    )
                }
                clickFlag()
            }
        }
    }

    private fun updateStart(check: Boolean) {
        reduce {
            copy(
                stateStart = check
            )
        }
    }

    private fun clickFlag() {
        flags.add(
            History(
                ++counterFlag,
                oldTime = (timer - lastTime).toTime(),
                time = timer.toTime()
            )
        )
        reduce {
            copy(
                stateFlags = flags
            )
        }
        lastTime = timer
//        val newList = mutableListOf<History>()
//        newList.addAll(flags.toList())
//        viewModelScope.launch {
//            stateFlags.emit(newList)
//        }
    }

    private fun screenOrientation(orientation: Orientation) {
        when (orientation) {
            Orientation.POR -> {
                reduce {
                    copy(
                        screenState = true
                    )
                }
            }

            Orientation.LAN -> {
                reduce {
                    copy(
                        screenState = false
                    )
                }
            }
        }
        //screenOrientation.value = orientation
    }

    private fun onStart() {
        viewModelScope.launch {

            counterFlag = useCase.getCounter()

            val state = useCase.getState()
            when (state) {
                "START" -> {
                    if (useCase.getDate() > 0L) {
                        timer = System.currentTimeMillis() - useCase.getDate() + useCase.getTimer()
                    }
                    useCase.getFlags().onEach { list ->
                        flags.addAll(list)
                        reduce {
                            copy(
                                stateBtn = BtnState.START,
                                stateStart = false,
                                stateStopWatch = timer.toTime(),
                                stateFlags = flags,
                                animationText = Animations(40.dp, 0.1f, 0.9f)
                            )
                        }
                    }.launchIn(viewModelScope)
                    stateBtn = BtnState.START
                    process.value = true
                    start()
                }

                "PAUSE" -> {
                    timer = useCase.getTimer()
                    useCase.getFlags().onEach { list ->
                        flags.clear()
                        flags.addAll(list)
                        reduce {
                            copy(
                                stateBtn = BtnState.PAUSE,
                                stateStart = false,
                                stateStopWatch = timer.toTime(),
                                stateFlags = flags,
                                animationText = Animations(40.dp, 0.1f, 0.9f)
                            )
                        }
                    }.launchIn(viewModelScope)
                    stateBtn = BtnState.PAUSE
                }

                else -> {
                    clear()
                }
            }
        }
    }

    private fun onStop() {
        when (stateBtn) {
            BtnState.START -> {
                viewModelScope.launch {
                    useCase.setState(stateBtn.name)
                    useCase.setTimer(timer)
                    useCase.setDate(System.currentTimeMillis())
                    useCase.setFlags(flags)
                    stateBtn = BtnState.START
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
                    useCase.setState(stateBtn.name)
                    useCase.setTimer(timer)
                    useCase.setDate(System.currentTimeMillis())
                    useCase.setFlags(flags)
                    stateBtn = BtnState.PAUSE
                    useCase.setCounter(counterFlag)
                }
            }

            BtnState.NONE -> {}
        }
    }

    private fun onDestroy() {
        when (stateBtn) {
            BtnState.START -> {
                viewModelScope.launch {
                    useCase.setState(stateBtn.name)
                    useCase.setTimer(timer)
                    useCase.setDate(System.currentTimeMillis())
                    useCase.setFlags(flags)
                    stateBtn = BtnState.START
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
                    useCase.setState(stateBtn.name)
                    useCase.setTimer(timer)
                    useCase.setDate(System.currentTimeMillis())
                    useCase.setFlags(flags)
                    stateBtn = BtnState.PAUSE
                    useCase.setCounter(counterFlag)
                }
            }

            BtnState.NONE -> {}
        }
    }

    private fun start() {
        viewModelScope.launch(Dispatchers.Unconfined) {
            while (process.value) {
                delay(10)
                timer += 10
                reduce {
                    copy(
                        stateStopWatch = timer.toTime()
                    )
                }
            }
        }
    }

    private fun clear() {
        reduce {
            copy(
                animationText = Animations(64.dp, 0.9f, 0.1f),
                stateStopWatch = "00:00:00",
                stateFlags = mutableListOf(),
                stateStart = true,
            )
        }
        stateBtn = BtnState.STOP
        viewModelScope.launch {
            useCase.setFlags(emptyList())
            useCase.setTimer(0)
            useCase.setDate(0)
            useCase.setCounter(0)
            useCase.setState(BtnState.NONE.name)
        }

        timer = 0
        counterFlag = 0
        lastTime = 0
        flags.clear()
        process.value = false
    }
}