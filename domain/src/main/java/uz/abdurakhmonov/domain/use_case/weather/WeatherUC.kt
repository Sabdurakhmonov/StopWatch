package uz.abdurakhmonov.domain.use_case.weather

import kotlinx.coroutines.flow.Flow
import uz.abdurakhmonov.data.remote.response.CoordinatesResponse
import uz.abdurakhmonov.data.remote.response.WeatherResponse
import uz.abdurakhmonov.domain.model.WeatherModel

interface WeatherUC {
    fun current(): Flow<WeatherModel>
    fun forecast(): Flow<WeatherResponse>
}