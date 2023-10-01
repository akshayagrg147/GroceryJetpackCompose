package com.grocery.mandixpress.common

import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.grocery.mandixpress.Utils.Text11_body2
import com.grocery.mandixpress.Utils.Text12_h1
import com.grocery.mandixpress.features.Home.ui.ui.theme.*


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CommonProgressBar(text: String = "Easy shop with Us") {


    Dialog(
        onDismissRequest = {}, properties = DialogProperties(
            dismissOnClickOutside = false,

            // Apply custom properties here if needed
        )
    ) {

        Box(
            modifier = Modifier
                .size(170.dp)

                .border(
                    1.dp, borderColor, androidx.compose.foundation.shape.RoundedCornerShape(16.dp)
                )
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color(0xAA000000)),
                    )
                )

        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.background)
                    .padding(10.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                CircularProgressIndicator(color = headingColor)
                Spacer(Modifier.height(10.dp))
                Text12_h1(text = text, color = headingColor)


            }
        }

    }
}

@Composable
fun AppButtonComponent(
    text: String,
    color: Color = Color.White,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(100.dp),
    elevation: ButtonElevation = ButtonDefaults.elevation(0.dp),
    background: Color = Color.White,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .background(background)
            .height(35.dp),
        elevation = elevation,
        shape = shape,

        ) {
        Text12_h1(
            text = text, color = Color.White
        )
    }
}

@Composable
fun LoadingBar() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

const val OTP_VIEW_TYPE_UNDERLINE = 1
const val OTP_VIEW_TYPE_BORDER = 2
const val OTP_VIEW_TYPE_NONE = 0

@Composable
fun OtpView(
    modifier: Modifier = Modifier,
    otpText: String = "",
    charColor: Color = Color(0XFFE8E8E8),
    charBackground: Color = Color.Transparent,
    charSize: TextUnit = 20.sp,
    containerSize: Dp = charSize.value.dp * 2,
    otpCount: Int = 6,

    type: Int = OTP_VIEW_TYPE_BORDER,
    enabled: Boolean = true,
    password: Boolean = false,
    passwordChar: String = "",
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
    onOtpTextChange: (String) -> Unit
) {
    BasicTextField(modifier = modifier, value = otpText,

        onValueChange = {
            if (it.length <= otpCount) {
                onOtpTextChange.invoke(it)
            }
        }, enabled = enabled, keyboardOptions = keyboardOptions, decorationBox = {
            Row(horizontalArrangement = Arrangement.SpaceAround) {
                repeat(otpCount) { index ->
                    Spacer(modifier = Modifier.width(2.dp))
                    CharView(
                        index = index,
                        text = otpText,
                        charColor = charColor,
                        charSize = charSize,
                        containerSize = containerSize,
                        type = type,
                        charBackground = charBackground,
                        password = password,
                        passwordChar = passwordChar,
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                }
            }
        })
}

@Composable
private fun CharView(
    index: Int,
    text: String,
    charColor: Color,
    charSize: TextUnit,
    containerSize: Dp,
    type: Int = OTP_VIEW_TYPE_UNDERLINE,
    charBackground: Color = Color.Transparent,
    password: Boolean = false,
    passwordChar: String = ""
) {
    val modifier = if (type == OTP_VIEW_TYPE_BORDER) {
        Modifier
            .size(containerSize)
            .border(
                width = 1.dp, color = charColor, shape = MaterialTheme.shapes.medium
            )
            .padding(bottom = 4.dp)
            .background(charBackground)
    } else Modifier
        .width(containerSize)
        .background(charBackground)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        val char = when {
            index >= text.length -> ""
            password -> passwordChar
            else -> text[index].toString()
        }
        Text(
            text = char,
            color = Color.Black,
            modifier = modifier.wrapContentHeight(),
            style = MaterialTheme.typography.body1,
            fontSize = charSize,
            textAlign = TextAlign.Center,
        )
        if (type == OTP_VIEW_TYPE_UNDERLINE) {
            Spacer(modifier = Modifier.height(2.dp))
            Box(
                modifier = Modifier
                    .background(charColor)
                    .height(1.dp)
                    .width(containerSize)
            )
        }
    }
}

@Composable
fun AppCustomChips(
    modifier: Modifier = Modifier,
    index: Int,
    selected: Boolean,
    title: String,
    unSelectedBackgroundColor: Color = Color.White,
    borderStroke: BorderStroke = BorderStroke(1.dp, Color.LightGray),
    onValueChange: (Int) -> Unit
) {

    Button(
        onClick = { onValueChange(index) },
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (selected) headingColor else unSelectedBackgroundColor,
            contentColor = if (selected) Color.White else Color.Black
        ),
        elevation = ButtonDefaults.elevation(0.dp),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp),
        modifier = modifier.padding(end = 10.dp),
        border = if (!selected) borderStroke else borderStroke
    ) {
        Text11_body2(
            text = title, if (selected) Color.White else Color.Black,
        )
    }

}


