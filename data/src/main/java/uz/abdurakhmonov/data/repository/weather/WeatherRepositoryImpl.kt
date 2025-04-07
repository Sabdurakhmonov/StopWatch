package uz.abdurakhmonov.data.repository.weather

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import uz.abdurakhmonov.data.model.LocationPermissionState
import uz.abdurakhmonov.data.remote.api.WeatherApi
import uz.abdurakhmonov.data.remote.response.CoordinatesResponse
import uz.abdurakhmonov.data.remote.response.WeatherResponse
import uz.abdurakhmonov.data.repository.location.LocationRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi,
    private val locationRepository: LocationRepository
): WeatherRepository {
    override fun current(): Flow<CoordinatesResponse> = flow {
        locationRepository.checkLocationPermission().collect {
            when(it){
                LocationPermissionState.GRANTED -> {
                    Log.d("AAA", "current: Grated")
                    locationRepository.getCurrentLocation().collect { location->
                        emit(api.current(lat = location.latitude.toString(), lon = location.longitude.toString(), lang = "ru"))
                    }
                }
                LocationPermissionState.DENIED -> {
                    emit(api.current(lat = "41.3110819", lon = "69.2405623", lang = "ru"))
                }
            }
        }
    }

    override fun forecast(): Flow<WeatherResponse>  = flow{
        WeatherResponse()
    }
}