package com.example.myweatherappv2.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.myweatherappv2.R
import com.example.myweatherappv2.ui.theme.DarkBlue

@Preview(showBackground = true)
@Composable
fun MainScreen() {
    Image(
        painter = painterResource(id = R.drawable.background),
        contentDescription = "background",
        modifier = Modifier
            .fillMaxSize()
            .alpha(0.6f),       // TODO(Поиграть с этим значением)
        contentScale = ContentScale.Crop
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp)
    ) {
        MainCard()
        TabLayout()
    }
}

@Preview(showBackground = true)
@Composable
fun MainCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = DarkBlue
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
                    text = "20 Oct 2023 / 12:30",
                    color = Color.White,
                    fontSize = 15.sp,
                    modifier = Modifier
                        .padding(top = 9.dp, start = 8.dp)
                )
                AsyncImage(
                    model = "https://cdn.weatherapi.com/weather/64x64/day/296.png",
                    contentDescription = "Weather icon",
                    modifier = Modifier
                        .padding(top = 3.dp, end = 8.dp)
                        .size(35.dp)
                )
            }
            Text(
                text = "London",
                color = Color.White,
                fontSize = 24.sp
            )
            Text(
                text = "23°C",
                color = Color.White,
                fontSize = 65.sp
            )
            Text(
                text = "Sunny",
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
                    text = "23°C / 25°C",
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
@Preview(showBackground = true)
@Composable
fun TabLayout() {
    val tabList = listOf("HOURS", "DAYS")
    val pagerState = rememberPagerState()

    Column(
        modifier = Modifier
            .padding(top = 8.dp)
            .clip(RoundedCornerShape(12.dp))
    ) {
        TabRow(
            selectedTabIndex = 0,
            indicator = {  },
            containerColor = DarkBlue,
            contentColor = Color.White
        ) {
            tabList.forEachIndexed { index, text ->
                Tab(
                    selected = false,
                    text = {
                        Text(text = text)
                    },
                    onClick = {
                        /*TODO*/
                    }
                )
            }
        }
        HorizontalPager(pageCount = tabList.size, state = pagerState) {

        }
    }
}
