package uz.abdurakhmonov.domain.model

import uz.abdurakhmonov.data.remote.response.CoordinatesResponse

data class WeatherModel(
    val location: String = "",
    val temperature: String = "",
    val humidity: String = "",
    val windSpeed: String = "",
    val maxTemp: String = "",
    val minTemp: String = "",
    val icon: String = "",
    val description: String = ""
)

fun CoordinatesResponse.toWeatherModel() = WeatherModel(
    location = name?:"",
    temperature = main?.temp?.toCelsius().toString(),
    humidity = main?.humidity.toString(),
    windSpeed = wind?.speed.toString(),
    maxTemp = main?.tempMax?.toCelsius()?:main?.temp?.toCelsius().toString(),
    minTemp = main?.tempMin?.toCelsius()?:main?.temp?.toCelsius().toString(),
    icon = weather?.firstOrNull()?.icon?:"",
    description = weather?.firstOrNull()?.description?:""
)

private fun Double.toCelsius(): String {
    val celsius = this - 273.15
    return String.format("%.0f°C", celsius)
}
