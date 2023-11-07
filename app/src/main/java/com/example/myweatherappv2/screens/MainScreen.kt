package com.example.myweatherappv2.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.myweatherappv2.R
import com.example.myweatherappv2.data.WeatherModel
import com.example.myweatherappv2.ui.theme.MyDarkBlueGray
import com.example.myweatherappv2.ui.theme.MyLightBlueGray
import kotlinx.coroutines.launch
import org.json.JSONArray
import kotlin.math.roundToInt

@Composable
fun MainScreen(
    daysList: MutableState<List<WeatherModel>>,
    currentDay: MutableState<WeatherModel>
) {
    Image(
        painter = painterResource(id = R.drawable.background),
        contentDescription = "background",
        modifier = Modifier
            .fillMaxSize()
            .alpha(0.65f),
        contentScale = ContentScale.Crop
    )

    Column(
        modifier = Modifier
            .padding(5.dp)
    ) {
        MainCard(currentDay)
        TabLayout(daysList, currentDay)
    }
}

@Composable
fun MainCard(currentDay: MutableState<WeatherModel>) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MyDarkBlueGray
        )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = currentDay.value.time,
                    color = Color.White,
                    fontSize = 15.sp,
                    modifier = Modifier
                        .padding(top = 9.dp, start = 8.dp)
                )
                AsyncImage(
                    model = "https:${currentDay.value.icon}",
                    contentDescription = "Weather icon",
                    modifier = Modifier
                        .padding(top = 3.dp, end = 8.dp)
                        .size(35.dp)
                )
            }
            Text(
                text = currentDay.value.city,
                color = Color.White,
                fontSize = 24.sp
            )
            Text(
                text = "${currentDay.value.currentTemp.roundToInt()}°C",
                color = Color.White,
                fontSize = 65.sp
            )
            Text(
                text = currentDay.value.condition,
                color = Color.White,
                fontSize = 16.sp
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = {
                        /*TODO*/
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search_24),
                        contentDescription = "Search button",
                        tint = Color.White
                    )
                }
                Text(
                    text = "${currentDay.value.minTemp.roundToInt()}°C /" +
                            " ${currentDay.value.maxTemp.roundToInt()}°C",
                    color = Color.White,
                    fontSize = 16.sp
                )
                IconButton(
                    onClick = {
                        /*TODO*/
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_sync_24),
                        contentDescription = "Sync button",
                        tint = Color.White
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabLayout(
    daysList: MutableState<List<WeatherModel>>,
    currentDay: MutableState<WeatherModel>
) {
    val tabList = listOf("HOURS", "DAYS")
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .padding(top = 8.dp)
            .clip(RoundedCornerShape(12.dp))
    ) {
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            containerColor = MyDarkBlueGray,
            contentColor = Color.White,
            divider = {
                Divider(color = MyLightBlueGray)
            }
        ) {
            tabList.forEachIndexed { index, text ->
                Tab(
                    selected = index == pagerState.currentPage,
                    text = {
                        Text(text = text)
                    },
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    }
                )
            }
        }
        HorizontalPager(
            pageCount = tabList.size,
            state = pagerState,
        ) { page ->
            MainList(
                list = daysList,
                currentDay = currentDay,
                page = page
            )
        }
    }
}

@Composable
fun MainList(
    list: MutableState<List<WeatherModel>>,
    currentDay: MutableState<WeatherModel>,
    page: Int
) {

}

@Composable
fun ListItem(item: WeatherModel, page: Int) {
    val mainText = if (page == 0) {
        "${item.currentTemp.roundToInt()}°"
    } else {
        "${item.minTemp.roundToInt()}° / ${item.maxTemp.roundToInt()}°"
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MyDarkBlueGray)
            .padding(
                horizontal = 8.dp,
                vertical = 5.dp
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(text = item.time, color = Color.White)
            Text(text = item.condition, color = Color.White)
        }
        Text(
            text = mainText,
            color = Color.White,
            fontSize = 25.sp
        )
        AsyncImage(
            model = "https:${item.icon}",
            contentDescription = "Weather icon",
            modifier = Modifier
                .size(35.dp)
        )
    }
    Divider(color = MyLightBlueGray)
}

private fun getWeatherByHours(hours: String): List<WeatherModel> {
    if (hours.isEmpty()) return emptyList()
    val hoursArray = JSONArray(hours)
    val list = ArrayList<WeatherModel>()
    for (i in 0 until hoursArray.length()) {
        val item = hoursArray.getJSONObject(i)
        list.add(WeatherModel(
            time = item.getString("time"),
            currentTemp = item.getDouble("temp_c"),
            condition = item.getJSONObject("condition").getString("text"),
            icon = item.getJSONObject("condition").getString("icon")
        ))
    }
    return list
}
