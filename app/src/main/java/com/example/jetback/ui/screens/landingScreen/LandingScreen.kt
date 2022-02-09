package com.example.jetback.ui.screens.landingScreen

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
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.jetback.ui.screens.landingScreen.components.ImageCard
import com.example.jetback.ui.screens.landingScreen.components.ImmersiveCluster
import com.example.jetback.ui.screens.landingScreen.components.ScaffoldTopBar
import com.example.jetback.ui.screens.landingScreen.components.SideNavItem
import com.example.jetback.ui.screens.landingScreen.components.SideNavigation
import com.example.jetback.ui.screens.landingScreen.components.SideNavigationType
import com.example.jetback.ui.screens.landingScreen.components.UserDp
import com.example.jetback.ui.theme.JetbackTheme

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun LandingScreen() {
    var isDarkTheme by remember { mutableStateOf(true) }
    var currentMovie: Pair<Pair<Int, String>, String>? by remember { mutableStateOf(null) }

    JetbackTheme(darkTheme = isDarkTheme) {
        val allStates: MutableList<Boolean> = remember { mutableListOf() }
        var isExpanded by remember { mutableStateOf(false) }

        SideNavigation(
            type = SideNavigationType.Overlay(
                padding = (
                    SideNavigation.SideNavItemSize +
                        SideNavigation.SideNavItemHPadding.times(2)
                    ).dp
            ),
            isExpanded = isExpanded,
            content = {
                Scaffold(
                    topBar = {
                        ScaffoldTopBar(isDarkTheme) {
                            isDarkTheme = !isDarkTheme
                        }
                    },
                ) {
                    Row {
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
        ) { isSideBarExpanded ->
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
    }
}
