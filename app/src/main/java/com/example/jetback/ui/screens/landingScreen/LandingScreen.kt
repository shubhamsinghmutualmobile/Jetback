package com.example.jetback.ui.screens.landingScreen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.Modifier
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
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                repeat(times = 10) {
                    Row(
                        modifier = Modifier.horizontalScroll(rememberScrollState())
                    ) {
                        repeat(times = 20) {
                            ImageCard()
                        }
                    }
                }
            }
        }
    }
}
