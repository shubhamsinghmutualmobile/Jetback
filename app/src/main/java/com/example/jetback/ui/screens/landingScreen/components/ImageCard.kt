package com.example.jetback.ui.screens.landingScreen.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.jetback.R
import com.example.jetback.ui.theme.LandingScreenTypography
import com.example.jetback.ui.utils.LeanbackListActions
import com.example.jetback.ui.utils.dpadFocusable
import kotlinx.coroutines.launch

object ImageCard {
    val listOfItems = listOf(
        R.drawable.kgf_poster to "K.G.F" to "\$2.99/day",
        R.drawable.ek_tha_tiger_poster to "Ek Tha Tiger" to "\$1.99/day",
        R.drawable.highway_poster to "Highway" to "\$1.57/day",
        R.drawable.lagaan_poster to "Lagaan" to "\$1.64/day",
        R.drawable.raazi_poster to "Raazi" to "\$0.99/day",
        R.drawable.war_poster to "WAR" to "\$1.25/day",
    )
    const val CardWidth = 300
    const val CardHeight = 150
    const val CardPadding = 16
}

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun ImageCard(
    columnState: LazyListState,
    rowState: LazyListState,
    rowItemIndex: Int,
    columnItemIndex: Int,
    getCurrentSelectedMovie: (Pair<Pair<Int, String>, String>?) -> Unit
) {
    val boxInteractionSource = remember { MutableInteractionSource() }
    val isItemFocused by boxInteractionSource.collectIsFocusedAsState()
    val imageToDisplay by remember { mutableStateOf(getRandomImageId()) }
    val coroutineScope = rememberCoroutineScope()
    val localDensity = LocalDensity.current

    LaunchedEffect(isItemFocused) {
        if (isItemFocused) {
            getCurrentSelectedMovie(imageToDisplay)
        }
    }

    Card(
        modifier = Modifier
            .size(width = ImageCard.CardWidth.dp, height = ImageCard.CardHeight.dp)
            .padding(ImageCard.CardPadding.dp)
            .dpadFocusable(
                boxInteractionSource = boxInteractionSource,
                isItemFocused = isItemFocused,
                leanbackListActions = LeanbackListActions(
                    onListLeft = {
                        val firstItemIndex = rowState.layoutInfo.visibleItemsInfo.first().index
                        if (firstItemIndex + 1 == rowItemIndex) {
                            coroutineScope.launch {
                                rowState.animateScrollBy(-(with(localDensity) { ImageCard.CardPadding.dp.toPx() * 2 }))
                            }
                        } else if (firstItemIndex == rowItemIndex) {
                            coroutineScope.launch {
                                rowState.animateScrollBy(-(with(localDensity) { ImageCard.CardWidth.dp.toPx() }))
                            }
                        }
                    },
                    onListRight = {
                        coroutineScope.launch {
                            rowState.animateScrollBy(
                                with(localDensity) {
                                    if (rowItemIndex == 1) {
                                        ImageCard.CardWidth.dp.toPx() - (ImageCard.CardPadding.dp.toPx() * 2)
                                    } else {
                                        ImageCard.CardWidth.dp.toPx()
                                    }
                                }
                            )
                        }
                    },
                    onListUp = {
                        val firstItemIndex = columnState.layoutInfo.visibleItemsInfo.first().index
                        if (firstItemIndex == columnItemIndex) {
                            coroutineScope.launch {
                                columnState.animateScrollBy(-(with(localDensity) { ImageCard.CardHeight.dp.toPx() }))
                            }
                        }
                    },
                    onListDown = {
                        coroutineScope.launch {
                            if (columnItemIndex != 0) {
                                columnState.animateScrollBy(
                                    with(localDensity) {
                                        if (columnItemIndex == 1) {
                                            ImageCard.CardHeight.dp.toPx() - (ImageCard.CardPadding.dp.toPx() * 2)
                                        } else {
                                            ImageCard.CardHeight.dp.toPx()
                                        }
                                    }
                                )
                            }
                        }
                    }
                )
            ),
        shape = RoundedCornerShape(10),
        onClick = {},
        interactionSource = MutableInteractionSource(),
        indication = rememberRipple(),
        elevation = 4.dp
    ) {
        Box {
            Image(
                painter = rememberImagePainter(data = imageToDisplay.first.first) {
                    crossfade(true)
                },
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Text(
                "$columnItemIndex, $rowItemIndex",
                modifier = Modifier
                    .padding(8.dp)
                    .background(
                        color = Color.Black,
                        shape = RoundedCornerShape(15)
                    )
                    .padding(8.dp)
                    .align(Alignment.TopStart)
            )
            AnimatedVisibility(
                visible = isItemFocused,
                enter = fadeIn(),
                exit = fadeOut(),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.Black
                                ),
                                endY = 250f
                            )
                        ),
                    contentAlignment = Alignment.BottomStart
                ) {
                    Text(
                        imageToDisplay.first.second, style = LandingScreenTypography.h6,
                        modifier = Modifier.padding(16.dp)
                    )
                    Text(
                        imageToDisplay.second, style = LandingScreenTypography.body1,
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.BottomEnd)
                    )
                }
            }
        }
    }
}

fun getRandomImageId(): Pair<Pair<Int, String>, String> {
    return ImageCard.listOfItems.shuffled().random()
}
