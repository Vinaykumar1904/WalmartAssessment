package com.example.walmartcountryassessment.data.mapper

import com.example.walmartcountryassessment.data.dto.CountryResponse
import com.example.walmartcountryassessment.domain.data.CountryDetails

//This Class Helps Us map Entity dto to Domain data classes
// use when we want to get only Required amount of data to complete the use case
class CountryMapperImpl:ICountryMapper<CountryResponse,CountryDetails> {

    override fun mapEntityToDomain(countryResponse: CountryResponse): CountryDetails {
        return CountryDetails(
            countryName = countryResponse.name,
            countryRegion = countryResponse.region,
            countryCode = countryResponse.code,
            countryCapital = countryResponse.capital
        )
    }
}