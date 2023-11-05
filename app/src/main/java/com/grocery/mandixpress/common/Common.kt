package com.grocery.mandixpress.common

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.grocery.mandixpress.R
import com.grocery.mandixpress.Utils.Text11_body2
import com.grocery.mandixpress.Utils.Text12_h1
import com.grocery.mandixpress.features.home.ui.ui.theme.*
import kotlin.math.roundToInt


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


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(background, shape = shape).clickable {
            onClick()
        },
        verticalArrangement = Arrangement.Center
    ) {
        Text12_h1(
            text = text,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)

            // Add padding if necessary
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


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SwipeButton(
    text: String,
    isComplete: Boolean,
    doneImageVector: ImageVector = Icons.Rounded.Done,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color(0xFF03A9F4),
    onSwipe: () -> Unit,
) {
    val width = 250.dp
    val widthInPx = with(LocalDensity.current) {
        width.toPx()
    }
    val anchors = mapOf(
        0F to 0,
        widthInPx to 1,
    )
    val swipeableState = rememberSwipeableState(0)
    val (swipeComplete, setSwipeComplete) = remember {
        mutableStateOf(false)
    }
    val (startSwipe, setStartSwipe) = remember {
        mutableStateOf(false)
    }
    val indicatorPosition = rememberSwipeableState(0)


    LaunchedEffect(key1 = swipeableState.currentValue) {
        if (swipeableState.currentValue == 1) {
            setSwipeComplete(true)
            onSwipe()
        }
    }
    LaunchedEffect(key1 = indicatorPosition.currentValue) {
        if (indicatorPosition.currentValue == 1) {
            setStartSwipe(true)
            onSwipe()
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .padding(horizontal = 5.dp, vertical = 16.dp)
            .clip(CircleShape)
            .background(backgroundColor)
            .animateContentSize()
            .then(
                if (swipeComplete) {
                    Modifier.fillMaxWidth()
                } else {
                    Modifier.fillMaxWidth()
                }
            )
            .requiredHeight(44.dp)
    ) {
        SwipeIndicator(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .alpha(if (swipeComplete) 0F else 1F)
                .offset {
                    IntOffset(swipeableState.offset.value.roundToInt(), 0)
                }
                .swipeable(
                    state = swipeableState,
                    anchors = anchors,
                    thresholds = { _, _ ->
                        FractionalThreshold(0.3F)
                    },
                    orientation = Orientation.Horizontal,
                ),
            backgroundColor = backgroundColor,
        )

        if (!isComplete) {
            // Display text if not complete
            AnimatedVisibility(visible = true) {
                Text12_h1(
                    text = text,
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .alpha(if (swipeComplete) 1F else 1F)
                        .padding(horizontal = 80.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
        if (swipeComplete || startSwipe) {
            // Display a button to start swiping again
            SwipeIndicator(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .alpha(if (swipeComplete) 1F else 1F)
                    .offset {
                        IntOffset(indicatorPosition.offset.value.roundToInt(), 0)
                    }
                    .swipeable(
                        state = indicatorPosition,
                        anchors = anchors,
                        thresholds = { _, _ ->
                            FractionalThreshold(0.3F)
                        },
                        orientation = Orientation.Horizontal,
                    ),
                backgroundColor = backgroundColor,
            )

        }

    }
}


@Composable
fun SwipeIndicator(
    modifier: Modifier = Modifier,
    backgroundColor: Color,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxHeight()
            .padding(2.dp)
            .clip(CircleShape)
            .aspectRatio(
                ratio = 1.0F,
                matchHeightConstraintsFirst = true,
            )
            .background(Color.White),
    ) {
        Image(
            painter = painterResource(id = R.drawable.swipe_button),
            contentDescription = "swipe button",
            modifier = Modifier.size(36.dp)
        )

    }
}
