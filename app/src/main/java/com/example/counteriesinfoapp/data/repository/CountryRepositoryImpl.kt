package com.example.counteriesinfoapp.data.repository

import com.example.counteriesinfoapp.data.global.network.CountriesApiService
import com.example.counteriesinfoapp.data.global.network.NetworkResult
import com.example.counteriesinfoapp.data.local.dao.CountryDao
import com.example.counteriesinfoapp.data.local.entity.CountryEntity
import com.example.counteriesinfoapp.utils.NetworkConnectivity
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class CountryRepositoryImpl(
    private val apiService: CountriesApiService,
    private val countryDao: CountryDao,
    private val networkHelper: NetworkConnectivity
) : CountryRepository {

    @OptIn(DelicateCoroutinesApi::class)
    override val countriesFlow = flow {
        emit(NetworkResult.Loading)

        val localData = countryDao.getAllCountries()
        if (localData.isNotEmpty()) emit(NetworkResult.Success(localData.map { it.toCountryResponse() }))

        if (networkHelper.isNetworkAvailable()) {
            try {
                val response = apiService.getCountries()
                if (response.isSuccessful) {
                    response.body()?.let { countries ->
                        val entities = countries.map {
                            CountryEntity(
                                it.id, it.name, it.abbreviation,
                                it.capital, it.currency,
                                it.phone, it.population, it.media.flag
                            )
                        }

                        emit(NetworkResult.Success(entities.map { it.toCountryResponse() })) // Update UI immediately

                        // Update database in the background
                        GlobalScope.launch { countryDao.insertAll(entities) }
                    } ?: emit(NetworkResult.Error("Empty response"))
                } else {
                    emit(NetworkResult.Error("API Error: ${response.message()}"))
                }
            } catch (e: Exception) {
                emit(NetworkResult.Error(e.localizedMessage ?: "Unexpected error"))
            }
        } else if (localData.isEmpty()) {
            emit(NetworkResult.Error("No data available. Connect to the internet to fetch data."))
        }
    }
}