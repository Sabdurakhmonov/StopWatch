package uz.abdurakhmonov.stopwatch.screen.weather_screen

import kotlinx.coroutines.flow.Flow

interface WeatherMapScreenContract {
    val uiState: Flow<UIState>

    data class UIState(
        val isLoading: Boolean = false,
        val location: String = "",
        val temperature: String = "",
        val humidity: String = "",
        val windSpeed: String = "",
        val maxTemp: String = "",
        val minTemp: String = "",
        val icon: String = "",
        val description: String = ""
    )

    fun intent(intent: Intent)

    sealed interface Intent {
        object LoadWeather : Intent
    }
}