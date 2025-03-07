package com.example.walmartcountryassessment.domain.usecases

import com.example.walmartcountryassessment.domain.data.CountryDetails
import com.example.walmartcountryassessment.data.utils.FetchCountryStates
import com.example.walmartcountryassessment.domain.repository.ICountryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class FetchCountryDetailsUseCase @Inject constructor(private val iCountryRepository: ICountryRepository):IFetchCountryDetailsUseCase {
    // Fetching Country Details from the Repository(Single Source of Truth for Data)
    override suspend operator fun invoke(): Flow<FetchCountryStates<List<CountryDetails>>> = iCountryRepository.getCountryDetails()
}