package com.example.counteriesinfoapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.counteriesinfoapp.data.local.dao.CountryDao
import com.example.counteriesinfoapp.data.local.entity.CountryEntity

@Database(entities = [CountryEntity::class], version = 1)
abstract class CountryDatabase : RoomDatabase() {
    abstract fun countryDao(): CountryDao
}