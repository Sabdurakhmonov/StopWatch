package uz.abdurakhmonov.data.remote.request

data class WeatherRequest(
    val lat: String,
    val long: String,
    val lang: String? = "uzb",
    val appid: String
)