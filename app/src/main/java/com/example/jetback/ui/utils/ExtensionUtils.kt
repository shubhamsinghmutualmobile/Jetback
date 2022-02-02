package com.example.jetback.ui.utils

import android.annotation.SuppressLint
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@SuppressLint("UnnecessaryComposedModifier")
@ExperimentalComposeUiApi
fun Modifier.dpadFocusable(
    borderWidth: Dp = 4.dp,
    unfocusedBorderColor: Color = Color(0x00f39c12),
    focusedBorderColor: Color = Color(0xfff39c12),
    focusBorderShape: Shape = RoundedCornerShape(10),
    shouldResizeOnFocus: Boolean = true,
    boxInteractionSource: MutableInteractionSource = MutableInteractionSource(),
    isItemFocused: Boolean,
    columnState: LazyListState? = null,
    rowState: LazyListState? = null,
    rowItemIndex: Int? = null,
    columnItemIndex: Int? = null,
    onClick: () -> Unit = {},
) = composed {
    val animatedBorderColor by animateColorAsState(
        targetValue =
        if (isItemFocused) focusedBorderColor
        else unfocusedBorderColor
    )
    val itemScale by animateFloatAsState(targetValue = if (isItemFocused && shouldResizeOnFocus) 1.1f else 1f)
    var previousPress: PressInteraction.Press? by remember {
        mutableStateOf(null)
    }
    val scope = rememberCoroutineScope()
    var boxSize by remember {
        mutableStateOf(IntSize(0, 0))
    }

    observeLazyListState(
        isItemFocused = isItemFocused,
        lazyListState = rowState,
        itemIndex = rowItemIndex,
        scope = scope,
        distanceToScroll = boxSize.width.toFloat()
    )

    observeLazyListState(
        isItemFocused = isItemFocused,
        lazyListState = columnState,
        itemIndex = columnItemIndex,
        scope = scope,
        distanceToScroll = boxSize.height.toFloat()
    )

    LaunchedEffect(isItemFocused) {
        previousPress?.let {
            if (!isItemFocused) {
                boxInteractionSource.emit(
                    PressInteraction.Release(
                        press = it
                    )
                )
            }
        }
    }

    this
        .scale(itemScale)
        .onGloballyPositioned {
            boxSize = it.size
        }
        .clickable(
            interactionSource = boxInteractionSource,
            indication = rememberRipple()
        ) {
            onClick()
        }
        .onKeyEvent {
            if (!listOf(Key.DirectionCenter, Key.Enter).contains(it.key)) {
                return@onKeyEvent false
            }
            when (it.type) {
                KeyEventType.KeyDown -> {
                    val press =
                        PressInteraction.Press(
                            pressPosition = Offset(
                                x = boxSize.width / 2f,
                                y = boxSize.height / 2f
                            )
                        )
                    scope.launch {
                        boxInteractionSource.emit(press)
                    }
                    previousPress = press
                    true
                }
                KeyEventType.KeyUp -> {
                    previousPress?.let { previousPress ->
                        onClick()
                        scope.launch {
                            boxInteractionSource.emit(
                                PressInteraction.Release(
                                    press = previousPress
                                )
                            )
                        }
                    }
                    true
                }
                else -> {
                    false
                }
            }
        }
        .focusable(interactionSource = boxInteractionSource)
        .border(
            width = borderWidth,
            color = animatedBorderColor,
            shape = focusBorderShape
        )
}

@SuppressLint("ComposableNaming")
@Composable
fun observeLazyListState(
    isItemFocused: Boolean,
    lazyListState: LazyListState?,
    itemIndex: Int?,
    scope: CoroutineScope,
    distanceToScroll: Float
) {
    if (isItemFocused) {
        if (lazyListState?.isScrollInProgress == false) {
            if (lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index == itemIndex) {
                SideEffect {
                    scope.launch {
                        itemIndex?.let { _ ->
                            lazyListState.animateScrollBy(distanceToScroll)
                        }
                    }
                }
            } else if (lazyListState.layoutInfo.visibleItemsInfo.first().index == itemIndex) {
                SideEffect {
                    scope.launch {
                        itemIndex.let { nnRowItemIndex ->
                            if (nnRowItemIndex > 0) {
                                lazyListState.animateScrollToItem(nnRowItemIndex - 1)
                            }
                        }
                    }
                }
            }
        }
    }
}
