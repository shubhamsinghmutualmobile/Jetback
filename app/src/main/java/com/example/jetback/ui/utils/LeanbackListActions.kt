package com.example.jetback.ui.utils

data class LeanbackListActions(
    val onListUp: () -> Unit = {},
    val onListDown: () -> Unit = {},
    val onListLeft: () -> Unit = {},
    val onListRight: () -> Unit = {},
)
