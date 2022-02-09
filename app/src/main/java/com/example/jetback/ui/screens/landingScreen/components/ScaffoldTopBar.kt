package com.example.jetback.ui.screens.landingScreen.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.jetback.R

@Composable
fun ScaffoldTopBar(
    isDarkTheme: Boolean,
    changeTheme: () -> Unit
) {
    TopAppBar(
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
