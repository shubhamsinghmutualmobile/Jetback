package com.example.jetback.ui.screens.landingScreen.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.jetback.R
import com.example.jetback.ui.utils.dpadFocusable

object SideNavigation {
    val SideNavigationTypography
        @Composable
        get() = Typography(
            body1 = TextStyle(
                fontSize = 16.sp,
                color = MaterialTheme.colors.onSurface,
                fontWeight = FontWeight.Normal
            ),
        )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SideNavigation() {
    val allStates: MutableList<Boolean> = remember { mutableListOf() }
    var isExpanded by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(vertical = 48.dp),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        repeat(6) { index ->
            val interactionSource = remember { MutableInteractionSource() }
            val isItemFocused by interactionSource.collectIsFocusedAsState()
            if (allStates.lastIndex > index) {
                allStates[index] = isItemFocused
            } else {
                allStates.add(index, isItemFocused)
            }
            LaunchedEffect(key1 = allStates.contains(true), block = {
                isExpanded = allStates.contains(true)
            })
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .dpadFocusable(
                        isItemFocused = false,
                        shouldResizeOnFocus = false,
                        boxInteractionSource = interactionSource
                    )
                    .padding(horizontal = 16.dp, vertical = 4.dp)
                    .animateContentSize()
            ) {
                Card(shape = CircleShape) {
                    Image(
                        painter = rememberImagePainter(data = R.drawable.ek_tha_tiger_poster) {
                            crossfade(true)
                        },
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        contentScale = ContentScale.Crop
                    )
                }
                AnimatedVisibility(
                    visible = isExpanded,
                    enter = fadeIn() + slideInHorizontally(),
                    exit = fadeOut() + slideOutHorizontally()
                ) {
                    Text(
                        text = "This is test text",
                        style = SideNavigation.SideNavigationTypography.body1,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }
    }
}
