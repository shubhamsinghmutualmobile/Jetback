package com.example.jetback.ui.screens.landingScreen.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.jetback.R
import kotlinx.coroutines.launch

@Composable
fun ScaffoldTopBar(
    scaffoldState: ScaffoldState,
    isDarkTheme: Boolean,
    changeTheme: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    TopAppBar(
        navigationIcon = {
            IconButton(
                onClick = {
                    coroutineScope.launch {
                        scaffoldState.drawerState.open()
                    }
                }
            ) {
                Icon(Icons.Default.Menu, null)
            }
        },
        title = { Text("Welcome to ${stringResource(id = R.string.app_name)}") },
        actions = {
            IconButton(onClick = {
                changeTheme()
            }) {
                Icon(
                    if (isDarkTheme) painterResource(id = R.drawable.ic_moon)
                    else painterResource(id = R.drawable.ic_sun),
                    "Toggle Theme"
                )
            }
        }
    )
}
