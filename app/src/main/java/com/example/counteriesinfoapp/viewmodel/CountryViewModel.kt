package com.example.counteriesinfoapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.counteriesinfoapp.data.global.model.CountryResponse
import com.example.counteriesinfoapp.data.global.network.NetworkResult
import com.example.counteriesinfoapp.data.repository.CountryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CountryViewModel(private val repository: CountryRepository) : ViewModel() {
    var searchQuery by mutableStateOf("")
    var populationFilter by mutableStateOf("All")

    private val _countriesState = MutableStateFlow<NetworkResult<List<CountryResponse>>>(NetworkResult.Loading)
    val countriesState: StateFlow<NetworkResult<List<CountryResponse>>> = _countriesState

    init {
        fetchCountries()
    }

    private fun fetchCountries() {
        viewModelScope.launch {
            repository.countriesFlow.collect { result ->
                _countriesState.value = result
            }
        }
    }

    fun filterCountries(countries: List<CountryResponse>): List<CountryResponse> {
        return countries.filter {
            (searchQuery.isBlank() || it.name.contains(searchQuery, true)) &&
                    when (populationFilter) {
                        "<1M" -> it.population < 1_000_000
                        "<5M" -> it.population < 5_000_000
                        "<10M" -> it.population < 10_000_000
                        else -> true
                    }
        }
    }

    fun retryFetchingCountries() {
        fetchCountries()
    }
}
