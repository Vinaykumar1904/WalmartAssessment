package com.example.walmartcountryassessment.domain.usecases

import com.example.walmartcountryassessment.domain.data.CountryDetails
import com.example.walmartcountryassessment.data.utils.FetchCountryStates
import kotlinx.coroutines.flow.Flow

interface IFetchCountryDetailsUseCase {
    suspend fun invoke():Flow<FetchCountryStates<List<CountryDetails>>>
}