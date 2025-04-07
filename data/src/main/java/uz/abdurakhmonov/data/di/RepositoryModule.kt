package uz.abdurakhmonov.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.abdurakhmonov.data.repository.location.LocationRepository
import uz.abdurakhmonov.data.repository.location.LocationRepositoryImpl
import uz.abdurakhmonov.data.repository.stop_watch.StopWatchRepository
import uz.abdurakhmonov.data.repository.stop_watch.StopWatchRepositoryImpl
import uz.abdurakhmonov.data.repository.weather.WeatherRepository
import uz.abdurakhmonov.data.repository.weather.WeatherRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface RepositoryModule {
    @[Binds Singleton]
    fun bindRepository(impl: StopWatchRepositoryImpl): StopWatchRepository

    @[Binds Singleton]
    fun bindLocationRepository(impl: LocationRepositoryImpl): LocationRepository

    @[Binds Singleton]
    fun bindWeatherRepository(impl: WeatherRepositoryImpl): WeatherRepository

}