package com.example.jetback.ui.screens.landingScreen.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.BoxWithConstraintsScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.jetback.ui.utils.dpadFocusable

object ImmersiveCluster {
    val ImmersiveClusterTypography = Typography(
        h1 = TextStyle(
            fontSize = 32.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold
        ),
        body1 = TextStyle(
            fontSize = 20.sp,
            color = Color.White,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Italic
        ),
    )

    @OptIn(ExperimentalAnimationApi::class)
    val defaultTransition = fadeIn() with fadeOut()

    const val HorizontalPadding = 32
    const val VerticalPadding = 8
}

@OptIn(ExperimentalAnimationApi::class, androidx.compose.ui.ExperimentalComposeUiApi::class)
@Composable
fun ImmersiveCluster(
    modifier: Modifier,
    movie: Pair<Pair<Int, String>, String>?,
) {
    BoxWithConstraints(
        modifier = modifier,
        contentAlignment = Alignment.CenterStart
    ) {
        AnimatedContent(
            targetState = movie?.first?.first,
            transitionSpec = { ImmersiveCluster.defaultTransition },
        ) { movieImage ->
            Image(
                modifier = Modifier
                    .fillMaxSize()
                    .dpadFocusable(
                        isItemFocused = false,
                        shouldResizeOnFocus = false,
                    ),
                painter = rememberImagePainter(
                    data = movieImage ?: Color.Black
                ) {
                    crossfade(true)
                },
                contentDescription = null,
                contentScale = ContentScale.Crop,
            )
        }
        Gradients()
        Column(
            verticalArrangement = Arrangement.Center
        ) {
            AnimatedContent(
                targetState = movie?.first?.second,
                transitionSpec = { ImmersiveCluster.defaultTransition }
            ) { movieName ->
                Text(
                    text = movieName.orEmpty(),
                    style = ImmersiveCluster.ImmersiveClusterTypography.h1,
                    modifier = Modifier.padding(start = ImmersiveCluster.HorizontalPadding.dp),
                )
            }
            AnimatedContent(
                targetState = movie?.second,
                transitionSpec = { ImmersiveCluster.defaultTransition }
            ) { movieRentPrice ->
                Text(
                    text = if (movieRentPrice.isNullOrBlank()) "" else "Rent for $movieRentPrice only",
                    style = ImmersiveCluster.ImmersiveClusterTypography.body1,
                    modifier = Modifier.padding(
                        horizontal = ImmersiveCluster.HorizontalPadding.dp,
                        vertical = ImmersiveCluster.VerticalPadding.dp
                    ),
                )
            }
        }
    }
}

@Composable
fun BoxWithConstraintsScope.Gradients() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color.Black,
                        Color.Transparent,
                    ),
                    endX = with(LocalDensity.current) { maxWidth.toPx() }
                )
            )
    ) {}
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Transparent,
                        Color.Black,
                    ),
                    endY = with(LocalDensity.current) { maxHeight.toPx() }
                )
            )
    ) {}
}
