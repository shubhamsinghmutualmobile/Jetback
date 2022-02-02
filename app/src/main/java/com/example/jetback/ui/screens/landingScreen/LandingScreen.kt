package com.example.jetback.ui.screens.landingScreen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.DrawerValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import com.example.jetback.ui.screens.landingScreen.components.ImageCard
import com.example.jetback.ui.screens.landingScreen.components.ScaffoldDrawer
import com.example.jetback.ui.screens.landingScreen.components.ScaffoldTopBar
import com.example.jetback.ui.theme.JetbackTheme
import kotlinx.coroutines.launch

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun LandingScreen() {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    var isDarkTheme by remember { mutableStateOf(true) }

    BackHandler(enabled = scaffoldState.drawerState.currentValue == DrawerValue.Open) {
        coroutineScope.launch {
            scaffoldState.drawerState.close()
        }
    }
    JetbackTheme(darkTheme = isDarkTheme) {
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                ScaffoldTopBar(scaffoldState, isDarkTheme) {
                    isDarkTheme = !isDarkTheme
                }
            },
            drawerContent = {
                ScaffoldDrawer(scaffoldState)
            }
        ) {
            val columnState = rememberLazyListState()
            LazyColumn(state = columnState) {
                repeat(times = 10) { columnItemIndex ->
                    item {
                        val rowState = rememberLazyListState()
                        LazyRow(state = rowState) {
                            repeat(times = 10) { rowItemIndex ->
                                item {
                                    ImageCard(columnState, rowState, rowItemIndex, columnItemIndex)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
