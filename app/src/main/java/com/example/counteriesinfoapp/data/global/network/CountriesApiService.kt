package com.example.counteriesinfoapp.data.global.network

import com.example.counteriesinfoapp.data.global.model.CountryResponse
import retrofit2.Response
import retrofit2.http.GET

interface CountriesApiService {
    @GET("countries")
    suspend fun getCountries(): Response<List<CountryResponse>>
}