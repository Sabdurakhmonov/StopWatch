package uz.abdurakhmonov.stopwatch.screen.weather_screen

import android.R.attr.description
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import uz.abdurakhmonov.domain.use_case.weather.WeatherUC
import javax.inject.Inject

@HiltViewModel
class WeatherMapScreenVM @Inject constructor(
    private val weatherUC: WeatherUC
) : ViewModel(), WeatherMapScreenContract {
    override val uiState =
        MutableStateFlow<WeatherMapScreenContract.UIState>(WeatherMapScreenContract.UIState())

    override fun intent(intent: WeatherMapScreenContract.Intent) {
        when (intent) {
            WeatherMapScreenContract.Intent.LoadWeather -> {
                init()
            }
        }
    }

    private fun init() {
        weatherUC.current().onStart {
            uiState.value = uiState.value.copy(isLoading = true)
        }.onEach {
            it.apply {
                uiState.value = uiState.value.copy(
                    location = location,
                    temperature = temperature,
                    humidity = humidity,
                    windSpeed = windSpeed,
                    maxTemp = maxTemp,
                    minTemp = minTemp,
                    isLoading = false,
                    icon = icon,
                    description = description
                )
            }
        }.launchIn(viewModelScope)
    }
}