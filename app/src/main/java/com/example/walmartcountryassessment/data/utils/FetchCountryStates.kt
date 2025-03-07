package com.example.walmartcountryassessment.data.utils

// These classes are used to represent the States of the Api Response
sealed class FetchCountryStates<out T> {
    object Loading: FetchCountryStates<Nothing>()
    data class Failure(val message:String): FetchCountryStates<Nothing>()
    data class Success<out T>(val data:T): FetchCountryStates<T>()
}
