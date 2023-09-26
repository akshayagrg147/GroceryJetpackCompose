package com.grocery.mandixpress.Utils

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.grocery.mandixpress.R
import com.grocery.mandixpress.features.Home.ui.ui.theme.*

@Composable
fun Text12_body1(text: String, color: Color = bodyTextColor, modifier: Modifier = Modifier,textAlign: TextAlign= TextAlign.Start) {
    Text(text = text, style = loginTypography.body1, color = color,fontSize = 12.sp, modifier = modifier, textAlign = textAlign)
}
@Composable
fun Text12_with_strikethrough(text1: String,text2:String, color: Color = bodyTextColor, modifier: Modifier = Modifier,style:TextStyle=loginTypography.body1) {
    Box(modifier = modifier) {
        Row(modifier = Modifier,Arrangement.spacedBy(8.dp )) {
            Text(
                text = text1,
                color = color,
                fontSize = 12.sp,
                style = LocalTextStyle.current.copy(textDecoration = TextDecoration.LineThrough)
            )
            Text(text = "$text2 ", color = color, fontSize = 12.sp, style = style)
        }

    }
}


@Composable
fun Text14_h1(text: String, color: Color = titleColor, modifier: Modifier = Modifier) {
    Text(text = text, style = loginTypography.h1, color = color, modifier = modifier,fontSize = 14.sp,maxLines = 2,overflow = TextOverflow.Ellipsis )
}
@Composable
fun Text14_h1_(text: String, color: Color = titleColor, modifier: Modifier = Modifier) {
    Text(text = text, style = loginTypography.h1, color = color, modifier = modifier,fontSize = 14.sp )
}

@Composable
fun Text16_h1(text: String, color: Color = titleColor, modifier: Modifier = Modifier,textAlign: TextAlign= TextAlign.Start) {
    Text(text = text, style = loginTypography.h1, color = color, modifier = modifier,fontSize = 16.sp, fontWeight = FontWeight.Bold, textAlign = textAlign,maxLines = 2,overflow = TextOverflow.Ellipsis )
}
@Composable
fun Text18_h1(text: String, color: Color = titleColor, modifier: Modifier = Modifier) {
    Text(text = text, style = loginTypography.h1, color = color, modifier = modifier,fontSize = 18.sp, fontWeight = FontWeight.Bold,maxLines = 2,overflow = TextOverflow.Ellipsis )
}
@Composable
fun Text12_h1(text: String, color: Color = titleColor, modifier: Modifier = Modifier,textAlign: TextAlign= TextAlign.Start) {
    Text(text = text, style = loginTypography.h4, color = color, modifier = modifier,fontSize = 12.sp,textAlign = textAlign,maxLines = 2,overflow = TextOverflow.Ellipsis )
}

@Composable
fun Text13_body1(text: String, color: Color = blackColor, modifier: Modifier = Modifier,textAlign: TextAlign=TextAlign.Start) {
    Text(text = text, style = loginTypography.body2, color = color, modifier = modifier, fontSize = 13.sp,textAlign = textAlign)
}
@Composable
fun Text11_body2(text: String, color: Color = blackColor, modifier: Modifier = Modifier) {
    Text(text = text, style = loginTypography.body2, color = color, modifier = modifier, fontSize = 11.sp)
}


@Composable
fun Text14_h2(text: String, color: Color = headingColor, modifier: Modifier = Modifier) {
    Text(text = text, style = loginTypography.h2, color = color, modifier = modifier,fontSize = 14.sp,maxLines = 2,overflow = TextOverflow.Ellipsis )
}
@Composable
fun Text10_h2(text: String, color: Color = headingColor, modifier: Modifier = Modifier) {
    Text(text = text, style = loginTypography.h2, color = color, modifier = modifier,fontSize = 10.sp,maxLines = 2,overflow = TextOverflow.Ellipsis )
}

@Composable
fun CommonButton(
    text: String,
    modifier: Modifier,
    color: Color = Color.White,
    backgroundColor: Color = titleColor,
    enabled: Boolean = true,
    onClick: (Boolean) -> Unit = {}
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, strokeColor),
    ) {
        Box(
            modifier = Modifier
                .background(if (enabled) backgroundColor else Color.Gray) // Change background color based on enabled state

                .clickable { onClick(enabled) }
        ) {
            Text13_body1(
                text = text,
                color = color,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(vertical = 15.dp)
            )
        }
    }

}

@Composable
fun CommonButton(
    icon: Int = 0 ,call:()->Unit
) {

    Card(
        elevation = 0.dp,
        shape = RoundedCornerShape(17.dp),
        border = BorderStroke(1.dp, strokeColor), modifier = Modifier.clickable { call.invoke() }
    ) {
        Box(modifier = Modifier.background(Color.White), contentAlignment = Alignment.Center) {
            androidx.compose.material.Icon(
                painter = painterResource(id = icon),
                contentDescription = "",
                tint = Color.Unspecified,
                modifier = Modifier.padding(10.dp)
            )
        }
    }

}

@Composable
fun CommonMathButton(
    icon: Int = 0 ,call:()->Unit
) {

    Card(
        elevation = 0.dp,
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(1.dp, strokeColor), modifier = Modifier.clickable { call.invoke() }
    ) {
        Box(modifier = Modifier.background(Color.White), contentAlignment = Alignment.Center) {
            androidx.compose.material.Icon(
                painter = painterResource(id = icon),
                contentDescription = "",
                tint = Color.Unspecified,
                modifier = Modifier.padding(5.dp)
            )
        }
    }

}
@Composable
fun CommonHeader(text: String, color: Color= titleColor, onClick: () -> Unit = {}) {
    Row {
        IconButton(
            onClick = { onClick() }
        ) {
            Icon(
                Icons.Default.ArrowBack,
                contentDescription = "",
            )
        }
        Text14_h1(
            text = text,
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .align(Alignment.CenterVertically),
            color = color
        )
    }
}
@Composable
fun CommonNumberField(
    text: MutableState<String>,
    placeholder: String,
    trailingIcon: Int=R.drawable.eye,
    iconColor: Color = Color.Transparent,
    keyboardType: KeyboardType = KeyboardType.Number,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier,
    size: Dp = 25.dp,
    enable: Boolean = true,
    onClick: (String) -> Unit = {}

) {
    TextField(
        value = text.value,
        onValueChange = { text.value=it
            onClick(it)},
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.White,
            focusedLabelColor = fadedTextColor,
            textColor = headingColor,
            unfocusedLabelColor = fadedTextColor,
            unfocusedIndicatorColor = Color.Transparent, // Remove unfocused underline
            focusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = fadedTextColor,

        ),
        label = { Text(text = placeholder) },
        trailingIcon = {
            Icon(
                painter = painterResource(id = trailingIcon),
                contentDescription = "",
                modifier = Modifier
                    .size(size),
                tint = iconColor,
            )
        },
        modifier = modifier
            .fillMaxWidth().border(1.dp, Color.Transparent, shape = RoundedCornerShape(4.dp))
            .clickable { onClick(text.value) },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        enabled = enable,
        textStyle = TextStyle(
            color = Color.Black
        )
    )
}
@Composable
fun CommonTextField(
    text: MutableState<String>,
    placeholder: String,
    trailingIcon: Int=R.drawable.eye,
    iconColor: Color = Color.Transparent,
    keyboardType: KeyboardType = KeyboardType.Text,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier,
    size: Dp = 25.dp,
    enable: Boolean = true,
    onClick: (String) -> Unit = {}

) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, borderColor, androidx.compose.foundation.shape.RoundedCornerShape(16.dp))
            .background(Color.White, androidx.compose.foundation.shape.RoundedCornerShape(16.dp))
            .clickable(enabled = enable) { /* Handle click if needed */ }
    ) {


        TextField(
            value = text.value,
            onValueChange = {
                text.value = it
                onClick(it)
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                focusedLabelColor = fadedTextColor,
                textColor = headingColor,
                unfocusedLabelColor = fadedTextColor,
                unfocusedIndicatorColor = Color.Transparent, // Remove unfocused underline
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = fadedTextColor
            ),
            label = { Text(text = placeholder) },
            trailingIcon = {
                Icon(
                    painter = painterResource(id = trailingIcon),
                    contentDescription = "",
                    modifier = Modifier
                        .size(size),
                    tint = iconColor,
                )
            },
            modifier = modifier
                .fillMaxWidth().border(1.dp, Color.Transparent, shape = RoundedCornerShape(4.dp))
                .clickable { onClick(text.value) },
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            enabled = enable,
            textStyle = TextStyle(
                color = Color.Black
            )
        )
    }
}


@Composable
fun CommonTextFieldNonEditable(
    text: MutableState<String>,
    placeholder: String,
    trailingIcon: Int=R.drawable.eye,
    iconColor: Color = Color.Transparent,
    keyboardType: KeyboardType = KeyboardType.Text,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier,
    size: Dp = 25.dp,
    enable: Boolean = false,
    onClick: (String) -> Unit = {}

) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, borderColor, androidx.compose.foundation.shape.RoundedCornerShape(16.dp))
            .background(Color.White, androidx.compose.foundation.shape.RoundedCornerShape(16.dp))
            .clickable(enabled = enable) { /* Handle click if needed */ }
    ) {


    TextField(
        value = text.value,
        onValueChange = {
            text.value = it
            onClick(it)
        },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.White,
            focusedLabelColor = fadedTextColor,
            textColor = headingColor,
            unfocusedLabelColor = fadedTextColor,
            unfocusedIndicatorColor = Color.Transparent, // Remove unfocused underline
            focusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = fadedTextColor,

        ),
        label = { Text(text = placeholder) },
        trailingIcon = {
            Icon(
                painter = painterResource(id = trailingIcon),
                contentDescription = "",
                modifier = Modifier
                    .size(size),
                tint = iconColor,
            )
        },
        modifier = modifier
            .fillMaxWidth().border(1.dp, Color.Transparent, shape = RoundedCornerShape(4.dp))
            .clickable { onClick(text.value) },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        enabled = enable,
        textStyle = TextStyle(
            color = Color.Black
        )
    )
}
}
