package com.debduttapanda.arrowfollower

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp

val TriangleShape = GenericShape { size, _ ->
    // 1)
    moveTo(size.width / 2f, 0f)

    // 2)
    lineTo(size.width, size.height)

    // 3)
    lineTo(0f, size.height)
}

data class ArrowFollower(
    val value: MutableState<Float>
) {
    val callback: (Float) -> Unit = {
        value.value = it
    }
    lateinit var currentValue: State<Float>

    @Composable
    fun arrowFollowed(
        active: Boolean
    ) = if (active) {
        Modifier.onGloballyPositioned {
            val center = (it.positionInWindow().x) + it.size.width / 2f
            callback(center)
        }
    } else {
        Modifier
    }

    @Composable
    fun Arrow(
        paddingValues: PaddingValues = PaddingValues(top = 16.dp),
        size: DpSize = DpSize(15.dp, 15.dp),
        color: Color = Color.LightGray,
        shape: Shape = TriangleShape
    ) {
        val density = LocalDensity.current.density
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .offset(x = (currentValue.value / density).dp - size.width / 2f)
                .size(size.width, size.height)
                .background(color, shape)
        )
    }
    companion object {
        @Composable
        fun rememberArrowFollower(key: Any = Unit): ArrowFollower {
            val data = remember(key) {
                ArrowFollower(
                    value = mutableStateOf(-100f)
                )
            }
            val animatedArrowCxx = animateFloatAsState(targetValue = data.value.value)
            data.currentValue = animatedArrowCxx
            return data
        }
    }
}
