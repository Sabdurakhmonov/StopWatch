package uz.abdurakhmonov.data.repository.location

import kotlinx.coroutines.flow.Flow
import uz.abdurakhmonov.data.model.Location
import uz.abdurakhmonov.data.model.LocationPermissionState

interface LocationRepository {
    fun getCurrentLocation(): Flow<Location>
    fun checkLocationPermission(): Flow<LocationPermissionState>
}