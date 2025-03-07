package com.example.walmartcountryassessment.domain.usecases

import com.example.walmartcountryassessment.domain.data.CountryDetails
import com.example.walmartcountryassessment.data.utils.FetchCountryStates
import com.example.walmartcountryassessment.domain.repository.ICountryRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

// This Class Provides Testcases for FetchCountryDetailsUseCase class
@ExperimentalCoroutinesApi
class FetchCountryDetailsUseCaseTest {
    private lateinit var fetchCountryDetailsUseCase: FetchCountryDetailsUseCase
    private val iCountryRepository: ICountryRepository = mockk()

    @Before
    fun setUp() {
        fetchCountryDetailsUseCase = FetchCountryDetailsUseCase(iCountryRepository)
    }

    @Test
    fun `invoke returns Success when repository emits data`() = runTest {
        val mockData = listOf(CountryDetails("India", "AS", "IN", "New Delhi"))
        val flow = flowOf(FetchCountryStates.Success(mockData))

        coEvery { iCountryRepository.getCountryDetails() } returns flow

        val result = fetchCountryDetailsUseCase.invoke().first()

        assertTrue(result is FetchCountryStates.Success)
        assertEquals(mockData, (result as FetchCountryStates.Success).data)
    }

    @Test
    fun `invoke returns Failure when repository throws an exception`() = runTest {
        val errorMessage = "Network Error"
        val flow: Flow<FetchCountryStates<List<CountryDetails>>> =
            flowOf(FetchCountryStates.Failure(errorMessage))

        coEvery { iCountryRepository.getCountryDetails() } returns flow

        val result = fetchCountryDetailsUseCase.invoke().first()

        assertTrue(result is FetchCountryStates.Failure)
        assertEquals(errorMessage, (result as FetchCountryStates.Failure).message)
    }
}