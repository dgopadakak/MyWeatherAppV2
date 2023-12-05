package com.example.myweatherappv2

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.myweatherappv2.data.WeatherModel
import com.example.myweatherappv2.screens.DialogSearch
import com.example.myweatherappv2.screens.MainScreen
import com.example.myweatherappv2.ui.theme.MyWeatherAppV2Theme
import org.json.JSONObject

const val API_KEY = "fe8e617c885e4cda976150431231708"
const val DAYS = 3

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val daysList = remember {
                mutableStateOf(listOf<WeatherModel>())
            }
            val dialogState = remember {
                mutableStateOf(false)
            }
            val currentDay = remember {
                mutableStateOf(WeatherModel())
            }
            getData(
                city = "London",
                context = this,
                daysList = daysList,
                currentDay = currentDay
            )
            MyWeatherAppV2Theme {
                MainScreen(
                    daysList = daysList,
                    currentDay = currentDay,
                    dialogState = dialogState,
                    onClickSync = {
                        getData("London", this@MainActivity, daysList, currentDay)
                    }
                )
                if (dialogState.value) {
                    DialogSearch(dialogState = dialogState)
                }
            }
        }
    }
}

private fun getData(
    city: String,
    context: Context,
    daysList: MutableState<List<WeatherModel>>,
    currentDay: MutableState<WeatherModel>
) {
    val url = "https://api.weatherapi.com/v1/forecast.json?" +
            "key=$API_KEY" +
            "&q=$city" +
            "&days=$DAYS" +
            "&aqi=no&alerts=no"
    val queue = Volley.newRequestQueue(context)
    val sRequest = StringRequest(
        Request.Method.GET,
        url,
        { response ->
            val list = getWeatherByDays(response)
            daysList.value = list
            currentDay.value = list[0]
        },
        { error ->
            Log.e("IWTSI", error.toString())
        }
    )
    queue.add(sRequest)
}

private fun getWeatherByDays(response: String): List<WeatherModel> {
    if (response.isEmpty()) return emptyList()

    val mainObject = JSONObject(response)
    val days = mainObject.getJSONObject("forecast").getJSONArray("forecastday")

    val city = mainObject.getJSONObject("location").getString("name")
    val result = ArrayList<WeatherModel>()
    for (i in 0 until days.length()) {
        val item = days[i] as JSONObject
        result.add(
            WeatherModel(
                city = city,
                time = item.getString("date"),
                currentTemp = 0.0,
                condition = item
                    .getJSONObject("day")
                    .getJSONObject("condition")
                    .getString("text"),
                icon = item
                    .getJSONObject("day")
                    .getJSONObject("condition")
                    .getString("icon"),
                maxTemp = item.getJSONObject("day").getDouble("maxtemp_c"),
                minTemp = item.getJSONObject("day").getDouble("mintemp_c"),
                hours = item.getJSONArray("hour").toString()
            )
        )
    }
    result[0] = result[0].copy(     // Для первого дня (это текущий день) заменяем часть информации
        time = mainObject.getJSONObject("current").getString("last_updated"),
        currentTemp = mainObject.getJSONObject("current").getDouble("temp_c")
    )
    return result
}
