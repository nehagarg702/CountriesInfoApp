package com.example.counteriesinfoapp.ui.activity

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import com.example.counteriesinfoapp.ui.screens.CountryScreen
import com.example.counteriesinfoapp.viewmodel.CountryViewModel
import org.koin.android.ext.android.get
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val viewModel: CountryViewModel = koinViewModel()
                val context : Context = get()
                CountryScreen(viewModel, context )
            }
        }
    }
}