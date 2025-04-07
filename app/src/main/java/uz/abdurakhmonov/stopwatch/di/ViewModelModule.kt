package uz.abdurakhmonov.stopwatch.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.abdurakhmonov.stopwatch.screen.watch_screen.WatchScreenContract
import uz.abdurakhmonov.stopwatch.screen.watch_screen.WatchScreenVM

@Module
@InstallIn(SingletonComponent::class)
interface ViewModelModule {
    @Binds
    fun bindHomeScreenVM(impl: WatchScreenVM): WatchScreenContract

}