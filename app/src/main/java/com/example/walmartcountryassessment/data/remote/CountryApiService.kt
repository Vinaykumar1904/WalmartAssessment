package com.example.walmartcountryassessment.data.remote

import com.example.walmartcountryassessment.data.dto.CountryResponse
import retrofit2.Response
import retrofit2.http.GET

//ApiService Provides Http method functions to communicate with the server

interface CountryApiService {

    @GET("32dcb892b06648910ddd40406e37fdab/raw/db25946fd77c5873b0303b858e861ce724e0dcd0/countries.json")
    suspend fun getCountry():Response<List<CountryResponse>>
}