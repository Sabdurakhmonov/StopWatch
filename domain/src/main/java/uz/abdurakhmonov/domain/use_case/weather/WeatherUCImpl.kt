package uz.abdurakhmonov.domain.use_case.weather

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import uz.abdurakhmonov.data.remote.response.WeatherResponse
import uz.abdurakhmonov.data.repository.weather.WeatherRepository
import uz.abdurakhmonov.domain.model.WeatherModel
import uz.abdurakhmonov.domain.model.toWeatherModel
import javax.inject.Inject

class WeatherUCImpl @Inject constructor(
    private val weatherRepository: WeatherRepository
) : WeatherUC {
    override fun current(): Flow<WeatherModel> = flow {
        weatherRepository.current().collect {
            emit(it.toWeatherModel())
        }
    }

    override fun forecast(): Flow<WeatherResponse> = flow {
        //emit(weatherRepository.forecast())
    }
}