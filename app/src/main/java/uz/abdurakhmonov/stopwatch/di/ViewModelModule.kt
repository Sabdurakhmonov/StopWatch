package uz.abdurakhmonov.stopwatch.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.abdurakhmonov.stopwatch.screen.home_screen.HomeScreenVM
import uz.abdurakhmonov.stopwatch.screen.home_screen.HomeScreenVMImpl

@Module
@InstallIn(SingletonComponent::class)
interface ViewModelModule {
    @Binds
    fun bindHomeScreenVM(impl: HomeScreenVMImpl): HomeScreenVM

}