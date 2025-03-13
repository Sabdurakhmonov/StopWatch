package uz.abdurakhmonov.domain.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import uz.abdurakhmonov.domain.use_case.LocalDateStoreUC
import uz.abdurakhmonov.domain.use_case.LocalDateStoreUCImpl
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
internal interface UseCaseModule {
    @[Binds ViewModelScoped]
    fun bindLocalDateStore(impl:LocalDateStoreUCImpl):LocalDateStoreUC
}