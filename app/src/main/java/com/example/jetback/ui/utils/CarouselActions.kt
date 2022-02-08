package com.example.jetback.ui.utils

data class CarouselActions(
    val onCarouselLeft: () -> Unit = {},
    val onCarouselRight: () -> Unit = {},
)
