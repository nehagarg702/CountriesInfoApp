package com.example.counteriesinfoapp.ui.screens

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.counteriesinfoapp.data.global.model.CountryResponse
import com.example.counteriesinfoapp.data.global.network.NetworkResult
import com.example.counteriesinfoapp.ui.components.CountryItem
import com.example.counteriesinfoapp.viewmodel.CountryViewModel


@Composable
fun CountryScreen(viewModel: CountryViewModel, context: Context) {
    val countriesState by viewModel.countriesState.collectAsState()
    var showDropdown by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header Section
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text("WrkSpot", style = MaterialTheme.typography.h6)
                Text("24th May 11:30 AM PST", style = MaterialTheme.typography.body2, color = Color.Gray)
            }
            IconButton(onClick = { /* Profile click action */ }) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Profile",
                    tint = MaterialTheme.colors.primary,
                    modifier = Modifier.size(32.dp)
                )
            }
        }

        Spacer(Modifier.height(8.dp))

        // Search Bar with Icons and Filter Dropdown
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = viewModel.searchQuery,
                onValueChange = { viewModel.searchQuery = it },
                placeholder = { Text("Search by Country") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon",
                        tint = Color.Gray
                    )
                },
                trailingIcon = {
                    if (viewModel.searchQuery.isNotEmpty()) {
                        IconButton(onClick = { viewModel.searchQuery = "" }) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Clear Search",
                                tint = Color.Gray
                            )
                        }
                    }
                },
                modifier = Modifier.weight(1f),
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color(0xFFF0F0FF),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )

            Spacer(Modifier.width(8.dp))

            Box {
                IconButton(onClick = { showDropdown = !showDropdown }) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Filter",
                        tint = MaterialTheme.colors.primary
                    )
                }

                DropdownMenu(
                    expanded = showDropdown,
                    onDismissRequest = { showDropdown = false }
                ) {
                    listOf("All", "<1M", "<5M", "<10M").forEach { filter ->
                        DropdownMenuItem(onClick = {
                            viewModel.populationFilter = filter
                            showDropdown = false
                        }) {
                            Text(
                                text = filter,
                                color = if (viewModel.populationFilter == filter) MaterialTheme.colors.primary else MaterialTheme.colors.onSurface
                            )
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        // Country List or Retry Button on Network Error
        when (countriesState) {
            is NetworkResult.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }

            is NetworkResult.Error -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = (countriesState as NetworkResult.Error).message,
                        color = Color.Red,
                        style = MaterialTheme.typography.body2
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = { viewModel.retryFetchingCountries() }) {
                        Text("Retry")
                    }
                }
            }

            is NetworkResult.Success -> {
                val filteredCountries = viewModel.filterCountries((countriesState as NetworkResult.Success<List<CountryResponse>>).data)
                if (filteredCountries.isEmpty()) {
                    Text(
                        text = "No results found.",
                        color = Color.Gray,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                } else {
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        items(filteredCountries) { country ->
                            CountryItem(country)
                        }
                    }
                }
            }
        }
    }
}



