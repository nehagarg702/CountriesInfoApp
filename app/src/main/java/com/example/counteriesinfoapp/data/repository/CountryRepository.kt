package com.example.counteriesinfoapp.data.repository

import com.example.counteriesinfoapp.data.global.model.CountryResponse
import com.example.counteriesinfoapp.data.global.network.CountriesApiService
import com.example.counteriesinfoapp.data.global.network.NetworkResult
import com.example.counteriesinfoapp.data.local.dao.CountryDao
import com.example.counteriesinfoapp.data.local.entity.CountryEntity
import com.example.counteriesinfoapp.utils.NetworkConnectivity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface CountryRepository {
    val countriesFlow: Flow<NetworkResult<List<CountryResponse>>>
}