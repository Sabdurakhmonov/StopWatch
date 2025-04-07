package uz.abdurakhmonov.data.repository.weather

import kotlinx.coroutines.flow.Flow
import uz.abdurakhmonov.data.remote.response.CoordinatesResponse
import uz.abdurakhmonov.data.remote.response.WeatherResponse

interface WeatherRepository {
    fun current(): Flow<CoordinatesResponse>
    fun forecast(): Flow<WeatherResponse>
}