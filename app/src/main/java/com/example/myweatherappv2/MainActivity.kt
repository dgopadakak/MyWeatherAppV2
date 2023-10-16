package com.example.myweatherappv2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.myweatherappv2.screens.MainScreen
import com.example.myweatherappv2.ui.theme.MyWeatherAppV2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyWeatherAppV2Theme {
                MainScreen()
            }
        }
    }
}
