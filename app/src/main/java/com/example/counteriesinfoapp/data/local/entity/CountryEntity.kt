package com.example.counteriesinfoapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.counteriesinfoapp.data.global.model.CountryResponse
import com.example.counteriesinfoapp.data.global.model.Media

@Entity(tableName = "countries")
data class CountryEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val abbreviation: String,
    val capital: String,
    val currency: String,
    val phone: String,
    val population: Long,
    val flagUrl: String
) {
    fun toCountryResponse() = CountryResponse(
        abbreviation, capital, currency, name, phone, population,
        Media(flagUrl, "", ""), id
    )
}