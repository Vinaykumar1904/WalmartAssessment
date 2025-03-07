package com.example.walmartcountryassessment.presentation.viewmodel

import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.Rule
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.walmartcountryassessment.domain.data.CountryDetails
import com.example.walmartcountryassessment.data.utils.FetchCountryStates
import com.example.walmartcountryassessment.domain.usecases.FetchCountryDetailsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
// This Class Provides Testcases for CountryViewModel
@OptIn(ExperimentalCoroutinesApi::class)
class CountryViewModelTest {
    private lateinit var viewModel: CountryViewModel
    private lateinit var CountryDetailsuseCase: FetchCountryDetailsUseCase
    private lateinit var testDispatcher: TestDispatcher

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        CountryDetailsuseCase = mockk()
        testDispatcher = StandardTestDispatcher()
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `fetchCountryDetails update countryStateFlow successfully`() = runTest(testDispatcher) {
        val fakeDomainList = listOf(
            CountryDetails("India", "AS", "IN", "New Delhi")
        )
        val fakeSuccessFlow = flow {
            emit(FetchCountryStates.Loading)
            delay(100L)
            emit(FetchCountryStates.Success(fakeDomainList))
        }
        coEvery { CountryDetailsuseCase() } returns fakeSuccessFlow

        viewModel = CountryViewModel(CountryDetailsuseCase)
        viewModel.fetchCountryDetails()
        val stateList = mutableListOf<FetchCountryStates<List<CountryDetails>>>()
        val job = launch {
            viewModel.countryStateFlow.collect{
                stateList.add(it)
            }
        }
        advanceUntilIdle()
        job.cancel()

        assert(stateList[0] is FetchCountryStates.Loading)
        assert(stateList[1] is FetchCountryStates.Success)
        val successResult = stateList[1] as FetchCountryStates.Success
        assert(successResult.data == fakeDomainList)
    }

    @Test
    fun `fetchCountryDetails failed due to api issue`() = runTest(testDispatcher) {
        val errorMessage = "Network Error"
        val fakeErrorFlow = flow {
            emit(FetchCountryStates.Loading)
            delay(100L)
            emit(FetchCountryStates.Failure(errorMessage))
        }
        coEvery { CountryDetailsuseCase() } returns fakeErrorFlow

        viewModel = CountryViewModel(CountryDetailsuseCase)
        viewModel.fetchCountryDetails()
        val uiStates = mutableListOf<FetchCountryStates<List<CountryDetails>>>()
        val job = launch {
            viewModel.countryStateFlow.collect {
                uiStates.add(it)
            }
        }
        advanceUntilIdle()
        job.cancel()

        assert(uiStates[0] is FetchCountryStates.Loading)
        assert(uiStates[1] is FetchCountryStates.Failure)
        val errorResult = uiStates[1] as FetchCountryStates.Failure
        assert(errorResult.message == errorMessage)
    }


    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}