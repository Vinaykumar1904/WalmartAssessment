package com.example.walmartcountryassessment.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.walmartcountryassessment.domain.data.CountryDetails
import com.example.walmartcountryassessment.data.utils.FetchCountryStates
import com.example.walmartcountryassessment.domain.usecases.IFetchCountryDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountryViewModel @Inject constructor(val iFetchCountryDetailsUseCase: IFetchCountryDetailsUseCase):ViewModel(){

    private val _countryStateFlow = MutableStateFlow<FetchCountryStates<List<CountryDetails>>>(
        FetchCountryStates.Loading)
    val countryStateFlow: StateFlow<FetchCountryStates<List<CountryDetails>>> = _countryStateFlow
    init {
        fetchCountryDetails()
    }
    // Fetching the States to display into UI
    fun fetchCountryDetails() {
        viewModelScope.launch {
            iFetchCountryDetailsUseCase.invoke()
                .catch {e->
                    val errorMsg = e.message
                    errorMsg?.let {
                        _countryStateFlow.value =  FetchCountryStates.Failure(it)
                    }
                }
                .collect{
                    _countryStateFlow.value = it
                }
        }
    }
}