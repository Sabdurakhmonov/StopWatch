package uz.abdurakhmonov.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.abdurakhmonov.data.repository.Repository
import uz.abdurakhmonov.data.repository.RepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface RepositoryModule {
    @[Binds Singleton]
    fun bindRepository(impl: RepositoryImpl): Repository
}