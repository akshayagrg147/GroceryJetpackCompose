package com.grocery.mandixpress.Utils

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.Alignment

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CircularProgressBar(
    progress: Float,
    isLoading: Boolean = false,
    progressColor: Color = Color.Blue,
    backgroundColor: Color = Color.LightGray,
    strokeWidth: Float = 4f, // Adjust the stroke width for smaller progress bars
    progressSize: Dp = 20.dp // Adjust the size of the CircularProgressIndicator
) {
    var animatedProgress by remember { mutableStateOf(0f) }

    LaunchedEffect(progress) {
        animatedProgress = progress
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Canvas(
            modifier = Modifier.size(progressSize) // Set the size of the Canvas
        ) {
            val centerX = size.width / 2f
            val centerY = size.height / 2f
            val radius = size.width / 2f - strokeWidth

            // Draw the background circle
            drawArc(
                color = backgroundColor,
                startAngle = 0f,
                sweepAngle = 360f,
                topLeft = Offset(centerX - radius, centerY - radius),
                size = Size(radius * 2, radius * 2),
                useCenter = false,
                style = Stroke(strokeWidth)
            )

            // Draw the progress arc (in one direction)
            drawArc(
                color = progressColor,
                startAngle = 90f,
                sweepAngle = -animatedProgress * 360f,
                topLeft = Offset(centerX - radius, centerY - radius),
                size = Size(radius * 2, radius * 2),
                useCenter = false,
                style = Stroke(strokeWidth)
            )

            // Draw the second progress arc (in the opposite direction)
            drawArc(
                color = progressColor,
                startAngle = -90f,
                sweepAngle = animatedProgress * 360f,
                topLeft = Offset(centerX - radius, centerY - radius),
                size = Size(radius * 2, radius * 2),
                useCenter = false,
                style = Stroke(strokeWidth)
            )
        }

        if (isLoading) {
            CircularProgressIndicator(
                color = MaterialTheme.colors.primary,
                strokeWidth = 4.dp,
                modifier = Modifier.run {
                    size(progressSize)
                                .align(Alignment.Center)
                }
            )
        }
    }
}