package com.example.walmartcountryassessment.domain.repository

import com.example.walmartcountryassessment.domain.data.CountryDetails
import com.example.walmartcountryassessment.data.utils.FetchCountryStates
import kotlinx.coroutines.flow.Flow

interface ICountryRepository {
    suspend fun getCountryDetails(): Flow<FetchCountryStates<List<CountryDetails>>>
}