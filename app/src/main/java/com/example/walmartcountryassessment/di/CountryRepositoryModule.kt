package com.example.walmartcountryassessment.di

import com.example.walmartcountryassessment.data.repository.CountryRepositoryImpl
import com.example.walmartcountryassessment.domain.repository.ICountryRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CountryRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCountryRepository(
        countryRepositoryImpl: CountryRepositoryImpl
    ):ICountryRepository
}