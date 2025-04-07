package uz.abdurakhmonov.data.remote.api

import retrofit2.http.GET
import retrofit2.http.Query
import uz.abdurakhmonov.data.remote.response.CoordinatesResponse
import uz.abdurakhmonov.data.remote.response.WeatherResponse

interface WeatherApi {
    companion object{
        const val API_KEY = "a5938363f2c0e0dfee2ffd7fa0ba7bbb"
    }
    @GET("data/2.5/weather")
    suspend fun current(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("lang") lang: String = "ru",
        @Query("appid") appid: String = API_KEY
    ): CoordinatesResponse

    @GET("data/2.5/forecast")
    suspend fun forecast(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") appid: String = API_KEY
    ): WeatherResponse

}