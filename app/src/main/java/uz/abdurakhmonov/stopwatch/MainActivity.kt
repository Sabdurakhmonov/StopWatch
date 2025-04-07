package uz.abdurakhmonov.stopwatch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import uz.abdurakhmonov.stopwatch.screen.watch_screen.StopWatchScreen
import uz.abdurakhmonov.stopwatch.screen.watch_screen.WatchScreenContract
import uz.abdurakhmonov.stopwatch.screen.watch_screen.WatchScreenVM
import uz.abdurakhmonov.stopwatch.screen.weather_screen.WeatherMapScreen
import uz.abdurakhmonov.stopwatch.screen.weather_screen.WeatherMapScreenContract
import uz.abdurakhmonov.stopwatch.screen.weather_screen.WeatherMapScreenVM
import uz.abdurakhmonov.stopwatch.ui.theme.StopWatchTheme
import uz.abdurakhmonov.stopwatch.utils.ItemAppbar
import uz.abdurakhmonov.stopwatch.utils.MyNavigationDrawer

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StopWatchTheme {

                val watchVm: WatchScreenContract = hiltViewModel<WatchScreenVM>()
                val weatherVm: WeatherMapScreenContract = hiltViewModel<WeatherMapScreenVM>()

                val state = watchVm.uiState.collectAsStateWithLifecycle(WatchScreenContract.UiState())
                val weatherState = weatherVm.uiState.collectAsStateWithLifecycle(WeatherMapScreenContract.UIState())


                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val scope = rememberCoroutineScope()

                var title by  remember {
                    mutableStateOf("StopWatch")
                }


                MyNavigationDrawer(
                    state = drawerState,
                    click = {it->
                        title = it
                    }
                ) {
                    Scaffold(
                        topBar = {
                            ItemAppbar(
                                startIcon = rememberVectorPainter(Icons.Default.Menu),
                                title = title,
                            ) {
                                scope.launch {
                                    drawerState.open()
                                }
                            }
                        }
                    ) { innerPadding ->
                        Column(modifier = Modifier.padding(innerPadding)) {
                            when (title) {
                                "StopWatch" -> StopWatchScreen(
                                    state = state,
                                    intent = watchVm::intent
                                )
                                else -> WeatherMapScreen(
                                    weatherState.value,
                                    intent = weatherVm::intent
                                )
                            }
                        }
                    }
                }

            }
        }
    }
}