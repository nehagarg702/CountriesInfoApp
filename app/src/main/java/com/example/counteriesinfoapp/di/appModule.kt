package com.example.counteriesinfoapp.di

import android.app.Application
import androidx.room.Room
import com.example.counteriesinfoapp.data.global.network.CountriesApiService
import com.example.counteriesinfoapp.data.global.network.NetworkConnectionInterceptor
import com.example.counteriesinfoapp.data.local.database.CountryDatabase
import com.example.counteriesinfoapp.data.repository.CountryRepository
import com.example.counteriesinfoapp.data.repository.CountryRepositoryImpl
import com.example.counteriesinfoapp.utils.NetworkConnectivity
import com.example.counteriesinfoapp.viewmodel.CountryViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val appModule = module {
    single(named("application")) { androidApplication() }
    single(named("applicationContext")) { get<Application>(named("application")).applicationContext }
    single { NetworkConnectivity(get(named("applicationContext"))) }
    single {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(NetworkConnectionInterceptor(get())) // Add the network interceptor
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl("https://api.sampleapis.com/countries/")
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CountriesApiService::class.java)
    }

    single {
        Room.databaseBuilder(
            androidContext(),
            CountryDatabase::class.java,
            "countries_db"
        ).build()
    }

    single { get<CountryDatabase>().countryDao() }

    single<CountryRepository> { CountryRepositoryImpl(get(), get(), get()) }

    viewModel { CountryViewModel(get()) }
}