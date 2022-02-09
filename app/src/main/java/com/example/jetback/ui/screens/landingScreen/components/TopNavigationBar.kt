package com.example.jetback.ui.screens.landingScreen.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.jetback.R
import com.example.jetback.ui.utils.CarouselActions
import com.example.jetback.ui.utils.dpadFocusable

object TopNavigationBar {
    const val HPadding = 16
}

sealed class TopNavigationBarType {
    class Centered(val startItem: @Composable () -> Unit) : TopNavigationBarType()
    object LeftAligned : TopNavigationBarType()
}

@ExperimentalComposeUiApi
@Composable
fun TopNavigationBar(
    type: TopNavigationBarType,
    totalItems: Int,
    endItem: @Composable () -> Unit,
    content: @Composable () -> Unit,
) {
    var currentSelectedIndex by remember { mutableStateOf(0) }
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(TopNavigationBar.HPadding.dp)
        ) {
            if (type is TopNavigationBarType.Centered) {
                type.startItem()
            }
            TopNavigationProgressBar(
                segments = totalItems,
                selectedIndex = currentSelectedIndex,
                onLeftDPad = {
                    if (currentSelectedIndex > 0) {
                        currentSelectedIndex -= 1
                    }
                },
                onRightDPad = {
                    if (currentSelectedIndex < 4) {
                        currentSelectedIndex += 1
                    }
                }
            )
            endItem()
        }
        content()
    }
}

@Composable
fun UserAccountPicture() {
    Surface(
        modifier = Modifier.size(32.dp),
        shape = CircleShape
    ) {
        Image(
            painter = rememberImagePainter(data = R.drawable.ek_tha_tiger_poster),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@ExperimentalComposeUiApi
@Composable
fun TopNavigationProgressBar(
    segments: Int,
    selectedIndex: Int,
    selectedSegmentColor: Color = Color.White,
    unselectedSegmentColor: Color = selectedSegmentColor.copy(alpha = 0.25f),
    progressBarWidth: Float = 0.35f,
    segmentShape: Shape = CircleShape,
    selectedSegmentScale: Float = 1.5f,
    onLeftDPad: () -> Unit,
    onRightDPad: () -> Unit,
    progressBarHorizontalPadding: Dp = 6.dp,
    segmentHeight: Dp = 12.dp,
    paddingBetweenSegments: Dp = 4.dp
) {
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    var isFocused by remember { mutableStateOf(false) }
    BoxWithConstraints(
        modifier = Modifier.fillMaxWidth(progressBarWidth),
    ) {
        val segmentWidth = (maxWidth / segments) - (progressBarHorizontalPadding * 2)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(selectedSegmentColor.copy(alpha = 0.15f), shape = CircleShape)
                .padding(progressBarHorizontalPadding)
                .focusRequester(focusRequester)
                .onFocusChanged {
                    isFocused = it.hasFocus
                }
                .dpadFocusable(
                    isItemFocused = false,
                    carouselActions = CarouselActions(
                        onCarouselLeft = {
                            if (selectedIndex == 0) {
                                focusManager.moveFocus(FocusDirection.Left)
                            } else {
                                if (isFocused) {
                                    onLeftDPad()
                                    isFocused = false
                                } else {
                                    isFocused = true
                                }
                            }
                        },
                        onCarouselRight = {
                            if (selectedIndex == segments - 1) {
                                focusManager.moveFocus(FocusDirection.Next)
                            } else {
                                if (isFocused) {
                                    onRightDPad()
                                    isFocused = false
                                } else {
                                    isFocused = true
                                }
                            }
                        }
                    )
                ),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(segments) { currentSegmentIndex ->
                val colorState by animateColorAsState(
                    targetValue = if (selectedIndex == currentSegmentIndex) selectedSegmentColor
                    else unselectedSegmentColor
                )
                val sizeState by animateFloatAsState(
                    targetValue =
                    if (selectedIndex == currentSegmentIndex)
                        (segmentWidth * selectedSegmentScale).value
                    else
                        segmentWidth.value
                )
                Surface(
                    modifier = Modifier
                        .width(sizeState.dp)
                        .padding(horizontal = paddingBetweenSegments)
                        .height(segmentHeight),
                    shape = segmentShape,
                    color = colorState
                ) {}
            }
        }
    }
}
