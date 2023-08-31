package com.grocery.mandixpress.features.Home.ui.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
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
        fontSize = 14.sp,
        color = bodyTextColor
    ),

    h5 = TextStyle(
        fontFamily = font_regular,
        fontWeight = FontWeight.W400,
        fontSize = 16.sp,
        color = fadedTextColor
    ),

    h4 = TextStyle(
        fontFamily = font_bold,
        fontWeight = FontWeight.W700,
        fontSize = 24.sp,
        color = titleColor
    ),

    h1 = TextStyle(
        fontFamily = font_bold,
        fontWeight = FontWeight.W700,
        fontSize = 20.sp,
        color = titleColor
    ),
    h2 = TextStyle(
        fontFamily = font_semibold,
        fontWeight = FontWeight.W600,
        fontSize = 18.sp,
        color = bodyTextColor
    ),
    h3 = TextStyle(
        fontFamily = font_semibold,
        fontWeight = FontWeight.W600,
        fontSize = 16.sp,
        color = bodyTextColor
    ),
    body2 = TextStyle(
        fontFamily = font_bold,
        fontWeight = FontWeight.W700,
        fontSize = 16.sp,
        color = whiteColor
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

val regVehicleTypography = Typography(
    body2 = TextStyle(
        fontFamily = font_semibold,
        fontWeight = FontWeight.W600,
        fontSize = 14.sp,
        color = fadedTextColor
    ),
    subtitle1 = TextStyle(
        fontFamily = font_regular,
        fontWeight = FontWeight.W400,
        fontSize = 12.sp,
        color = fadedTextColor
    )

)

val searchStTypography = Typography(
    body1 = TextStyle(
        fontFamily = font_bold,
        fontWeight = FontWeight.W700,
        fontSize = 12.sp,
        color = titleColor
    )
)


val scanTypography = Typography(
    body1 = TextStyle(
        fontFamily = font_regular,
        fontWeight = FontWeight.W400,
        fontSize = 24.sp,
        color = Color.White
    )
)

val chargeTypography = Typography(
    body1 = TextStyle(
        fontFamily = font_bold,
        fontWeight = FontWeight.W700,
        fontSize = 32.sp,
        color = sec20timer
    )
)

val billTypography = Typography(
    body1 = TextStyle(
        fontFamily = font_semibold,
        fontWeight = FontWeight.W500,
        fontSize = 9.sp,
        color = darkFadedColor
    )
)
