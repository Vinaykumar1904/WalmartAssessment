package com.example.walmartcountryassessment.data.repository

import app.cash.turbine.test
import com.example.walmartcountryassessment.MainCoroutineRule
import com.example.walmartcountryassessment.data.dto.CountryResponse
import com.example.walmartcountryassessment.data.dto.Currency
import com.example.walmartcountryassessment.data.dto.Language
import com.example.walmartcountryassessment.data.mapper.ICountryMapper
import com.example.walmartcountryassessment.data.remote.CountryApiService
import com.example.walmartcountryassessment.domain.data.CountryDetails
import com.example.walmartcountryassessment.data.utils.FetchCountryStates
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response
import java.net.UnknownHostException
// This Test Class Provides Testcases for CountryRepositoryImpl class
@ExperimentalCoroutinesApi
class CountryRepositoryImplTest {


    @get:Rule
    val coroutineRule =
        MainCoroutineRule()

    private lateinit var repository: CountryRepositoryImpl
    private val apiService: CountryApiService = mockk()
    private val mapper: ICountryMapper<CountryResponse, CountryDetails> = mockk()

    @Before
    fun setUp() {
        repository = CountryRepositoryImpl(apiService, mapper)
    }

    @Test
    fun `getCountryDetails should emit Loading and then Success when API call is successful`() =
        runTest {
            // Arrange
            val countryResponse = CountryResponse(
                name = "India",
                capital = "New Delhi",
                code = "IN",
                currency = Currency("INR", "Indian rupee", "₹"),
                demonym = "Indian",
                flag = "https://restcountries.eu/data/ind.svg",
                language = Language("hi", "hin", "Hindi", "Hindi"),
                region = "AS"
            )
            val expectedCountryDetails = CountryDetails(
                countryName = "India",
                countryRegion = "AS",
                countryCode = "IN",
                countryCapital = "New Delhi"
            )

            coEvery { apiService.getCountry() } returns Response.success(listOf(countryResponse))
            every { mapper.mapEntityToDomain(countryResponse) } returns expectedCountryDetails

            // Act & Assert
            repository.getCountryDetails().test {
                // First emission should be Loading
                assertTrue(awaitItem() is FetchCountryStates.Loading)
                // Second Emission should be Success
                assertEquals(
                    FetchCountryStates.Success(listOf(expectedCountryDetails)),
                    awaitItem()
                )
                // Ensure flow completes
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `getCountryDetails should emit Failure when API call is unsuccessful`() = runTest {
        // Arrange
        coEvery { apiService.getCountry() } returns Response.error(
            500,
            "Server Error".toResponseBody()
        )

        // Act & Assert
        repository.getCountryDetails().test {
            //First Emission Loading
            assertTrue(awaitItem() is FetchCountryStates.Loading)
            // Second Emission Failure
            assertEquals(
                FetchCountryStates.Failure("Something Went Wrong. Please try again later..."),
                awaitItem()
            )
            // Ensure flow completes
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getCountryDetails should emit Failure when API response body is null`() = runTest {
        // Arrange
        coEvery { apiService.getCountry() } returns Response.success(null)

        // Act & Assert
        repository.getCountryDetails().test {
            // First Emission Loading
            assertTrue(awaitItem() is FetchCountryStates.Loading)
            // Second Emission Failure
            assertEquals(
                FetchCountryStates.Failure("Something Went Wrong. Please try again later..."),
                awaitItem()
            )
            // Ensure flow completes
            cancelAndIgnoreRemainingEvents()
        }
    }
}