package com.grocery.mandixpress.features.Home.ui.screens


import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape



import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import kotlin.math.min
import kotlin.math.tan


class BottomCurvedShape : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val bottomLeftRadius = 16.dp
        val bottomRightRadius = 16.dp
        val path = Path().apply {
            moveTo(0f, 0f)
            lineTo(size.width.toFloat(), 0f)
            lineTo(size.width.toFloat(), size.height)
            quadraticBezierTo(
                size.width / 2f,
                size.height + bottomRightRadius.value,
                0f,
                size.height - bottomLeftRadius.value
            )
            close()
        }
        return Outline.Generic(path)
    }
}
