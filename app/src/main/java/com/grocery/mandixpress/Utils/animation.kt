package com.grocery.mandixpress.Utils

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically

// Example function to define custom enter and exit animations
@OptIn(ExperimentalAnimationApi::class)
fun AnimatedContentScope<String>.customSlideAnimations() {
    // Define the enter animation using slideInVertically
    // Slide in from the top
    slideInVertically(
        initialOffsetY = { -it },
        animationSpec = tween(durationMillis = 500)
    )

    // Define the exit animation using slideOutVertically
    // Slide out to the bottom
    slideOutVertically(
        targetOffsetY = { it },
        animationSpec = tween(durationMillis = 500)
    )
}
