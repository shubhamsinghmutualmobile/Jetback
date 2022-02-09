package com.example.jetback.ui.screens.landingScreen.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.jetback.R
import com.example.jetback.ui.utils.dpadFocusable

object SideNavigation {
    const val UserDpExpandedSize = 54f
    const val UserDpCollapsedSize = 32f
    const val MasterVerticalPadding = 16
    const val UserDpStartPadding = 12
    const val SmallSpacer = 4
    const val SideNavItemHPadding = 16
    const val SideNavItemVPadding = 4
    const val SideNavItemTextPadding = 8
    const val SideNavItemSize = 24
    const val UnselectedTextAlpha = 0.25f
    val SideNavigationTypography
        @Composable
        get() = Typography(
            body1 = TextStyle(
                fontSize = 16.sp,
                color = MaterialTheme.colors.onSurface,
                fontWeight = FontWeight.Normal
            ),
            h6 = TextStyle(
                fontSize = 18.sp,
                color = MaterialTheme.colors.onSurface,
                fontWeight = FontWeight.Bold
            )
        )
}

sealed class SideNavigationType {
    class Overlay(val padding: Dp) : SideNavigationType()
    object Pushing : SideNavigationType()
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SideNavigation(
    type: SideNavigationType = SideNavigationType.Pushing,
    isExpanded: Boolean,
    expandedColor: Color = MaterialTheme.colors.surface,
    collapsedColor: Color = Color.Black,
    content: @Composable () -> Unit,
    sideNavigationContent: @Composable ColumnScope.(Boolean) -> Unit,
) {
    val columnBgColorState by animateColorAsState(
        targetValue = if (isExpanded) expandedColor else collapsedColor,
    )

    when (type) {
        is SideNavigationType.Pushing -> {
            Row(
                modifier = Modifier.fillMaxSize()
            ) {
                SideNavigationContent(columnBgColorState, type, sideNavigationContent, isExpanded)
                content()
            }
        }
        is SideNavigationType.Overlay -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.CenterStart,
            ) {
                Row {
                    Spacer(
                        modifier = Modifier.padding(start = type.padding)
                    )
                    content()
                }
                SideNavigationContent(columnBgColorState, type, sideNavigationContent, isExpanded)
            }
        }
    }
}

@Composable
private fun SideNavigationContent(
    columnBgColorState: Color,
    type: SideNavigationType,
    sideNavigationContent: @Composable ColumnScope.(Boolean) -> Unit,
    isExpanded: Boolean
) {
    Column(
        modifier = Modifier
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        columnBgColorState,
                        if (type == SideNavigationType.Pushing || !isExpanded) columnBgColorState else Color.Transparent,
                    ),
                    startX = if (isExpanded) 250f else 0f
                )
            )
            .fillMaxHeight()
            .padding(vertical = SideNavigation.MasterVerticalPadding.dp),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        sideNavigationContent(isExpanded)
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SideNavItem(
    allStates: MutableList<Boolean>,
    index: Int,
    isExpanded: Boolean,
    onExpandChange: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isItemFocused by interactionSource.collectIsFocusedAsState()

    if (allStates.lastIndex > index) {
        allStates[index] = isItemFocused
    } else {
        allStates.add(index, isItemFocused)
    }

    LaunchedEffect(key1 = allStates.contains(true), block = {
        onExpandChange()
    })

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .dpadFocusable(
                isItemFocused = false,
                shouldResizeOnFocus = false,
                boxInteractionSource = interactionSource
            )
            .padding(
                horizontal = SideNavigation.SideNavItemHPadding.dp,
                vertical = SideNavigation.SideNavItemVPadding.dp
            )
            .animateContentSize()
    ) {
        Card(shape = CircleShape) {
            Image(
                painter = rememberImagePainter(data = R.drawable.ek_tha_tiger_poster) {
                    crossfade(true)
                },
                contentDescription = null,
                modifier = Modifier.size(SideNavigation.SideNavItemSize.dp),
                contentScale = ContentScale.Crop
            )
        }
        val textColorState by animateColorAsState(
            targetValue = if (isItemFocused) MaterialTheme.colors.onSurface
            else MaterialTheme.colors.onSurface.copy(alpha = SideNavigation.UnselectedTextAlpha)
        )
        AnimatedVisibility(
            visible = isExpanded,
            enter = fadeIn() + slideInHorizontally(),
            exit = fadeOut() + slideOutHorizontally()
        ) {
            Text(
                text = "This is test text",
                style = SideNavigation.SideNavigationTypography.body1.copy(
                    color = textColorState
                ),
                modifier = Modifier.padding(start = SideNavigation.SideNavItemTextPadding.dp)
            )
        }
    }
}

@Composable
fun UserDp(isExpanded: Boolean) {
    val userDpState by animateFloatAsState(
        targetValue = if (isExpanded) SideNavigation.UserDpExpandedSize
        else SideNavigation.UserDpCollapsedSize
    )

    Column(
        modifier = Modifier
            .padding(start = SideNavigation.UserDpStartPadding.dp)
            .animateContentSize()
    ) {
        Surface(
            shape = CircleShape,
            modifier = Modifier.size(userDpState.dp)
        ) {
            Image(
                painter = rememberImagePainter(data = R.drawable.raazi_poster),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.padding(vertical = SideNavigation.SmallSpacer.dp))

        AnimatedVisibility(
            visible = isExpanded,
            enter = fadeIn() + slideInHorizontally(),
            exit = fadeOut() + slideOutHorizontally()
        ) {
            Text(
                text = "Shubham Singh",
                style = SideNavigation.SideNavigationTypography.h6,
            )
        }
    }
}
