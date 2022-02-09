package com.example.jetback.ui.screens.landingScreen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import com.example.jetback.ui.screens.landingScreen.components.ImageCard
import com.example.jetback.ui.screens.landingScreen.components.ImmersiveCluster
import com.example.jetback.ui.screens.landingScreen.components.ScaffoldDrawer
import com.example.jetback.ui.screens.landingScreen.components.ScaffoldTopBar
import com.example.jetback.ui.screens.landingScreen.components.SideNavItem
import com.example.jetback.ui.screens.landingScreen.components.SideNavigation
import com.example.jetback.ui.screens.landingScreen.components.UserDp
import com.example.jetback.ui.theme.JetbackTheme
import kotlinx.coroutines.launch

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun LandingScreen() {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    var isDarkTheme by remember { mutableStateOf(true) }
    var currentMovie: Pair<Pair<Int, String>, String>? by remember { mutableStateOf(null) }

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
            Row {
                val allStates: MutableList<Boolean> = remember { mutableListOf() }
                var isExpanded by remember { mutableStateOf(false) }

                SideNavigation(isExpanded = isExpanded) { isSideBarExpanded ->
                    UserDp(isExpanded = isSideBarExpanded)
                    repeat(6) { index ->
                        SideNavItem(
                            allStates = allStates,
                            index = index,
                            isExpanded = isSideBarExpanded,
                            onExpandChange = { isExpanded = allStates.contains(true) }
                        )
                    }
                }

                BoxWithConstraints(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.TopStart
                ) {
                    ImmersiveCluster(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.6f),
                        movie = currentMovie
                    )
                    val columnState = rememberLazyListState()
                    LazyColumn(
                        state = columnState,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        item {
                            Spacer(modifier = Modifier.height(maxHeight / 2))
                        }
                        repeat(times = 10) { columnItemIndex ->
                            item {
                                val rowState = rememberLazyListState()
                                LazyRow(state = rowState) {
                                    repeat(times = 10) { rowItemIndex ->
                                        item {
                                            ImageCard(
                                                columnState = columnState,
                                                rowState = rowState,
                                                rowItemIndex = rowItemIndex,
                                                columnItemIndex = columnItemIndex,
                                                getCurrentSelectedMovie = { currentSelectedMovie ->
                                                    currentMovie = currentSelectedMovie
                                                }
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
