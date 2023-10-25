package com.example.myweatherappv2

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.myweatherappv2.screens.MainScreen
import com.example.myweatherappv2.ui.theme.MyWeatherAppV2Theme

const val API_KEY = "fe8e617c885e4cda976150431231708"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyWeatherAppV2Theme {
                MainScreen()
            }
        }
        getData(
            city = "London",
            context = this
        )
    }
}

private fun getData(city: String, context: Context) {
    val url = "https://api.weatherapi.com/v1/forecast.json?" +
            "key=$API_KEY" +
            "&q=$city" +
            "&days=3" +
            "&aqi=no&alerts=no"
    val queue = Volley.newRequestQueue(context)
    val sRequest = StringRequest(
        Request.Method.GET,
        url,
        { response ->
            Log.i("IWTSI", "Response: $response")
        },
        { error ->
            Log.e("IWTSI", error.toString())
        }
    )
    queue.add(sRequest)
}
