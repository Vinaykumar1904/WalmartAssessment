package com.example.walmartcountryassessment.data.repository

import android.util.Log
import com.example.walmartcountryassessment.data.dto.CountryResponse
import com.example.walmartcountryassessment.data.mapper.ICountryMapper
import com.example.walmartcountryassessment.data.remote.CountryApiService
import com.example.walmartcountryassessment.data.utils.Constants.CONNECTION_ERROR
import com.example.walmartcountryassessment.data.utils.Constants.GENERIC_ERROR
import com.example.walmartcountryassessment.data.utils.Constants.TAG
import com.example.walmartcountryassessment.domain.data.CountryDetails
import com.example.walmartcountryassessment.data.utils.FetchCountryStates
import com.example.walmartcountryassessment.domain.repository.ICountryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.net.UnknownHostException
import javax.inject.Inject

// This class is used to fetch the Response from Api
// And we emit the Appropriate state(Loading, Success, Failure)
// Also use Mapper to Transform the Data into required Data classes
class CountryRepositoryImpl @Inject constructor(
    private val countryApiService: CountryApiService,
    private val iMapperImp: ICountryMapper<CountryResponse, CountryDetails>
) : ICountryRepository {

    override suspend fun getCountryDetails(): Flow<FetchCountryStates<List<CountryDetails>>> =
        flow {
            try {
                emit(FetchCountryStates.Loading)
                val response = countryApiService.getCountry()
                if (!response.isSuccessful) {
                    emit(FetchCountryStates.Failure(GENERIC_ERROR))
                    Log.e(
                        TAG,
                        "Error occurred while fetching data. Response code: ${response.code()}"
                    )
                } else {
                    if (response.body() == null) {
                        emit(FetchCountryStates.Failure(GENERIC_ERROR))
                        Log.e(TAG, "Received empty response from the server")
                    } else {
                        val countryDetails: List<CountryDetails>? = response.body()?.map {
                            iMapperImp.mapEntityToDomain(it)
                        }

                        emit(FetchCountryStates.Success(countryDetails!!))
                        Log.d(TAG, "Country Fetched Successfully!!!")
                    }
                }
            } catch (e: UnknownHostException) {
                emit(FetchCountryStates.Failure(CONNECTION_ERROR))
                Log.e(TAG, "Network connectivity issue ${e.message}")
            } catch (e: Exception) {
                emit(FetchCountryStates.Failure(GENERIC_ERROR))
                Log.e(TAG, "Exception ${e.message}")
            }
        }.flowOn(Dispatchers.IO)
}