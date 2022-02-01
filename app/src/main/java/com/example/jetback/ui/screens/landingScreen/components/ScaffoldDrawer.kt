package com.example.jetback.ui.screens.landingScreen.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ScaffoldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ScaffoldDrawer(scaffoldState: ScaffoldState) {
    val coroutineScope = rememberCoroutineScope()
    IconButton(onClick = {
        coroutineScope.launch {
            scaffoldState.drawerState.close()
        }
    }) {
        Icon(Icons.Default.ArrowBack, "Close Drawer")
    }
}
