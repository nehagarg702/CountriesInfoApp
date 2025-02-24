package com.example.counteriesinfoapp.data.global.model

data class CountryResponse(
    val abbreviation: String,
    val capital: String,
    val currency: String,
    val name: String,
    val phone: String,
    val population: Long,
    val media: Media,
    val id: Int
)