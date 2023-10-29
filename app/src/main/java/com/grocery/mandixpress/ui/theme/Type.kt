package com.grocery.mandixpress.features.home.ui.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.Font
import com.grocery.mandixpress.R

val font_regular = FontFamily(
    Font(R.font.manrope_regular)
)

val font_semibold = FontFamily(
    Font(R.font.manrope_semibold)
)

val font_bold = FontFamily(
    Font(R.font.manrope_bold)
)

val Typography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
)

val loginTypography = Typography(
    body1 = TextStyle(
        fontFamily = font_regular,
        fontWeight = FontWeight.W400,

    ),

    h5 = TextStyle(
        fontFamily = font_semibold,
        fontWeight = FontWeight.W400,
    ),

    h4 = TextStyle(
        fontFamily = font_semibold,
        fontWeight = FontWeight.W500,

    ),

    h1 = TextStyle(
        fontFamily = font_semibold,
        fontWeight = FontWeight.W800,
    ),
    h2 = TextStyle(
        fontFamily = font_semibold,
        fontWeight = FontWeight.W300,
    ),
    h3 = TextStyle(
        fontFamily = font_semibold,
        fontWeight = FontWeight.W600,

    ),

    body2 = TextStyle(
        fontFamily = font_bold,
        fontWeight = FontWeight.W700,

    ),

    subtitle1 = TextStyle(
        fontFamily = font_regular,
        fontWeight = FontWeight.W400,
        fontSize = 10.sp,
        color = fadedTextColor
    ),

    subtitle2 = TextStyle(
        fontFamily = font_regular,
        fontWeight = FontWeight.W700,
        fontSize = 20.sp,
        color = headingColor
    )

)










