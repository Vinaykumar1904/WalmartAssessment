package com.example.walmartcountryassessment.di

import com.example.walmartcountryassessment.domain.usecases.FetchCountryDetailsUseCase
import com.example.walmartcountryassessment.domain.usecases.IFetchCountryDetailsUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class FetchCountryDetailsUseCaseModule {

    @Binds
    @Singleton
    abstract fun bindFetchCountryDetailsUseCase(
        fetchCountryDetailsUseCase: FetchCountryDetailsUseCase
    ):IFetchCountryDetailsUseCase

}