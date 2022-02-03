package com.example.jetback.ui.screens.landingScreen.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
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
import com.google.accompanist.pager.PagerState
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

    @OptIn(ExperimentalAnimationApi::class)
    val defaultTransition = fadeIn(animationSpec = tween(700)) +
        fadeIn() with fadeOut()

    const val CarouselHeightFactor = 0.6f
    const val CarouselButtonAlpha = 0.25f
    const val CarouselBottomRowHPadding = 32
    const val CarouselBottomRowVPadding = 8
    const val VerticalGradientStart = 250f
    const val VerticalGradientEnd = 700f
    const val HorizontalGradientStart = -800f
    const val HorizontalGradientEnd = 1000f
}

@OptIn(
    ExperimentalPagerApi::class, androidx.compose.ui.ExperimentalComposeUiApi::class,
    ExperimentalAnimationApi::class
)
@Composable
fun Carousel(
    listOfMovies: List<Pair<Pair<Int, String>, String>> = ImageCard.listOfItems
) {
    val carouselState = rememberPagerState()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(Carousel.CarouselHeightFactor),
        contentAlignment = Alignment.BottomEnd,
    ) {
        CarouselImage(
            carouselState = carouselState,
            listOfMovies = listOfMovies
        )
        CarouselGradients()
        // Only in order to populate the HorizontalPagerIndicator
        HorizontalPager(
            count = listOfMovies.size,
            modifier = Modifier.wrapContentSize(),
            state = carouselState,
            content = {}
        )
        CarouselBottomRow(
            carouselState = carouselState,
            listOfMovies = listOfMovies
        )
    }
}

@OptIn(
    ExperimentalPagerApi::class, ExperimentalAnimationApi::class,
    androidx.compose.ui.ExperimentalComposeUiApi::class
)
@Composable
private fun CarouselImage(
    carouselState: PagerState,
    listOfMovies: List<Pair<Pair<Int, String>, String>>
) {
    val coroutineScope = rememberCoroutineScope()
    AnimatedContent(
        targetState = carouselState.targetPage,
        transitionSpec = { Carousel.defaultTransition },
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
            )
    ) { index ->
        val imagePainter =
            rememberImagePainter(data = listOfMovies[index].first.first)
        Image(
            imagePainter,
            null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}

@OptIn(ExperimentalPagerApi::class, ExperimentalAnimationApi::class)
@Composable
private fun CarouselBottomRow(
    carouselState: PagerState,
    listOfMovies: List<Pair<Pair<Int, String>, String>>,
) {
    val buttonColors = ButtonDefaults.buttonColors(
        backgroundColor = MaterialTheme.colors.primary.copy(alpha = Carousel.CarouselButtonAlpha)
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = Carousel.CarouselBottomRowHPadding.dp,
                vertical = Carousel.CarouselBottomRowVPadding.dp
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        Column(
            verticalArrangement = Arrangement.Bottom
        ) {
            AnimatedContent(
                targetState = listOfMovies[carouselState.currentPage].first.second,
                transitionSpec = { Carousel.defaultTransition }
            ) { movieTitle ->
                Text(
                    movieTitle,
                    style = Carousel.CarouselTypography.h1,
                    modifier = Modifier.padding(Carousel.CarouselBottomRowVPadding.dp)
                )
            }
            Button(
                onClick = {},
                shape = CircleShape,
                colors = buttonColors
            ) {
                AnimatedContent(
                    targetState = listOfMovies[carouselState.currentPage].second,
                    transitionSpec = { Carousel.defaultTransition }
                ) { movieRent ->
                    Text("Rent now for $movieRent")
                }
            }
        }
        HorizontalPagerIndicator(
            pagerState = carouselState,
            modifier = Modifier
                .background(
                    color = MaterialTheme.colors.onSurface.copy(alpha = Carousel.CarouselButtonAlpha),
                    shape = CircleShape
                )
                .padding(8.dp)
        )
    }
}

@Composable
private fun CarouselGradients() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Transparent,
                        Color.Black
                    ),
                    startY = Carousel.VerticalGradientStart,
                    endY = Carousel.VerticalGradientEnd
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
                    startX = Carousel.HorizontalGradientStart,
                    endX = Carousel.HorizontalGradientEnd
                )
            )
    ) {}
}
