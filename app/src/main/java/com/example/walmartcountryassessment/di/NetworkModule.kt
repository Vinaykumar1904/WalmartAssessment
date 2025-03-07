package com.example.walmartcountryassessment.di

import com.example.walmartcountryassessment.data.dto.CountryResponse
import com.example.walmartcountryassessment.data.mapper.CountryMapperImpl
import com.example.walmartcountryassessment.data.mapper.ICountryMapper
import com.example.walmartcountryassessment.data.remote.CountryApiService
import com.example.walmartcountryassessment.domain.data.CountryDetails
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun providesLoggingInterceptor():HttpLoggingInterceptor{
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return loggingInterceptor
    }

    @Provides
    @Singleton
    fun providesClient(loggingInterceptor: HttpLoggingInterceptor):OkHttpClient{
        val client = OkHttpClient.Builder().apply {
            addInterceptor(loggingInterceptor)
        }.build()
        return client
    }

    @Provides
    @Singleton
    fun providesRetrofit(client: OkHttpClient):Retrofit{
        val retrofit:Retrofit by lazy {
            Retrofit.Builder().apply {
                baseUrl("https://gist.github.com/peymano-wmt/")
                client(client)
                addConverterFactory(GsonConverterFactory.create())
            }.build()
        }
        return retrofit
    }

    @Provides
    @Singleton
    fun providesCountryApiService(retrofit: Retrofit): CountryApiService {
        return retrofit.create(CountryApiService::class.java)
    }

    @Provides
    @Singleton
    fun providesMapper():ICountryMapper<CountryResponse,CountryDetails>{
        return CountryMapperImpl()
    }
}