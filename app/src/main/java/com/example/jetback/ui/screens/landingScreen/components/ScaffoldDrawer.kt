package com.example.jetback.ui.screens.landingScreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.jetback.R
import com.example.jetback.ui.theme.LandingScreenTypography
import com.example.jetback.ui.utils.dpadFocusable
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
    UserDetails()
    Spacer(modifier = Modifier.padding(vertical = 8.dp))
    OptionRow("Search Movies", Icons.Default.Search)
    OptionRow("My Account", Icons.Default.Person)
    OptionRow("Favourites", Icons.Default.Favorite)
    OptionRow("Settings", Icons.Default.Settings)
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun OptionRow(title: String, icon: ImageVector) {
    val interactionSource by remember { mutableStateOf(MutableInteractionSource()) }
    val isItemFocused by interactionSource.collectIsFocusedAsState()
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .dpadFocusable(
                shouldResizeOnFocus = false,
                boxInteractionSource = interactionSource,
                isItemFocused = isItemFocused
            )
            .padding(horizontal = 8.dp, vertical = 8.dp),
    ) {
        Icon(icon, null, modifier = Modifier.size(32.dp))
        Text(title, modifier = Modifier.padding(start = 16.dp))
    }
}

@Composable
private fun UserDetails() {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card(
            shape = CircleShape,
            modifier = Modifier
                .size(100.dp)
                .padding(8.dp),
        ) {
            Image(
                painterResource(id = R.drawable.ic_launcher_background),
                null,
                contentScale = ContentScale.Crop
            )
            Image(
                painterResource(id = R.drawable.ic_launcher_foreground),
                null,
                contentScale = ContentScale.Crop
            )
        }
        Column {
            Text(
                "Shubham Singh",
                style = LandingScreenTypography.h6,
                modifier = Modifier.padding(4.dp)
            )
            Text(
                "shubham.singh@mutualmobile.com",
                style = LandingScreenTypography.body2,
                modifier = Modifier.padding(4.dp)
            )
        }
    }
}
