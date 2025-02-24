package com.example.counteriesinfoapp

import android.app.Application
import com.example.counteriesinfoapp.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CountriesInfoApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@CountriesInfoApp)
            modules(appModule)
        }
    }
}