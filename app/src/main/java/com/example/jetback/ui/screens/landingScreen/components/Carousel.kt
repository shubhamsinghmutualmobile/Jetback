package com.example.jetback.ui.screens.landingScreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.jetback.ui.utils.dpadFocusable
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

object Carousel {
    val CarouselTypography
        @Composable
        get() = Typography(
            h1 = TextStyle(
                fontSize = 32.sp,
                color = MaterialTheme.colors.onSurface,
                fontWeight = FontWeight.Bold
            )
        )
}

@OptIn(ExperimentalPagerApi::class, androidx.compose.ui.ExperimentalComposeUiApi::class)
@Composable
fun Carousel(
    listOfMovies: List<Pair<Pair<Int, String>, String>> = ImageCard.listOfItems
) {
    val carouselState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    val buttonColors = ButtonDefaults.buttonColors(
        backgroundColor = MaterialTheme.colors.primary.copy(alpha = 0.25f)
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.6f),
        contentAlignment = Alignment.BottomEnd,
    ) {
        val imagePainter =
            rememberImagePainter(data = listOfMovies[carouselState.currentPage].first.first) {
                crossfade(250)
                allowHardware(true)
            }
        Image(
            imagePainter,
            null,
            modifier = Modifier
                .fillMaxSize()
                .dpadFocusable(
                    isItemFocused = false,
                    isCarousel = true,
                    carouselActionLeft = {
                        coroutineScope.launch {
                            if (!carouselState.isScrollInProgress) {
                                if (carouselState.currentPage > 0) {
                                    carouselState.animateScrollToPage(carouselState.currentPage - 1)
                                } else {
                                    carouselState.animateScrollToPage(carouselState.pageCount - 1)
                                }
                            }
                        }
                    },
                    carouselActionRight = {
                        coroutineScope.launch {
                            if (!carouselState.isScrollInProgress) {
                                if (carouselState.currentPage < carouselState.pageCount - 1) {
                                    carouselState.animateScrollToPage(carouselState.currentPage + 1)
                                } else {
                                    carouselState.animateScrollToPage(0)
                                }
                            }
                        }
                    }
                ),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black
                        ),
                        startY = 250f,
                        endY = 700f
                    )
                )
        ) {}
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color.Black,
                            Color.Transparent,
                        ),
                        startX = -800f,
                        endX = 1000f
                    )
                )
        ) {}
        HorizontalPager(
            count = listOfMovies.size,
            modifier = Modifier.wrapContentSize(),
            state = carouselState
        ) {}
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            Column(
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    listOfMovies[carouselState.currentPage].first.second,
                    style = Carousel.CarouselTypography.h1,
                    modifier = Modifier.padding(8.dp)
                )
                Button(
                    onClick = {},
                    shape = CircleShape,
                    colors = buttonColors
                ) {
                    Text("Rent for ${listOfMovies[carouselState.currentPage].second}")
                }
            }
            HorizontalPagerIndicator(
                pagerState = carouselState,
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colors.onSurface.copy(alpha = 0.25f),
                        shape = CircleShape
                    )
                    .padding(8.dp)
            )
        }
    }
}
