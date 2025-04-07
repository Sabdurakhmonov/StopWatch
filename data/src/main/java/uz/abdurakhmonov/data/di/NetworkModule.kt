package uz.abdurakhmonov.data.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @[Provides Singleton]
    fun providesOkHttpClient(@ApplicationContext context: Context): OkHttpClient =
        OkHttpClient.Builder().addInterceptor(ChuckerInterceptor.Builder(context).build()).build()

    @[Provides Singleton]
    fun providesWeatherRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder().baseUrl("https://api.openweathermap.org/").client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build()

}