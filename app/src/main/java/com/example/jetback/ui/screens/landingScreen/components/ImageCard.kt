package com.example.jetback.ui.screens.landingScreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.jetback.R
import com.example.jetback.ui.utils.dpadFocusable

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun ImageCard() {
    Card(
        modifier = Modifier
            .size(width = 300.dp, height = 150.dp)
            .padding(16.dp)
            .dpadFocusable(),
        shape = RoundedCornerShape(10),
        onClick = {},
        interactionSource = MutableInteractionSource(),
        indication = rememberRipple()
    ) {
        Image(
            painter = rememberImagePainter(data = R.drawable.app_icon_your_company) {
                crossfade(true)
            },
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    }
}
