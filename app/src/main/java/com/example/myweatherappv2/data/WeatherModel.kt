package com.example.myweatherappv2.data

data class WeatherModel(
    val city: String = "",
    val time: String = "",
    val currentTemp: Double = 0.0,
    val condition: String = "",
    val icon: String = "",
    val maxTemp: Double = 0.0,
    val minTemp: Double = 0.0,
    val hours: String = "",
)
