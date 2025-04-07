package uz.abdurakhmonov.domain.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import uz.abdurakhmonov.domain.use_case.stop_watch.LocalDateStoreUC
import uz.abdurakhmonov.domain.use_case.stop_watch.LocalDateStoreUCImpl
import uz.abdurakhmonov.domain.use_case.weather.WeatherUC
import uz.abdurakhmonov.domain.use_case.weather.WeatherUCImpl

@Module
@InstallIn(ViewModelComponent::class)
internal interface UseCaseModule {

    @[Binds ViewModelScoped]
    fun bindLocalDateStore(impl:LocalDateStoreUCImpl):LocalDateStoreUC

    @[Binds ViewModelScoped]
    fun bindsWeatherUseCase(impl:WeatherUCImpl):WeatherUC
}