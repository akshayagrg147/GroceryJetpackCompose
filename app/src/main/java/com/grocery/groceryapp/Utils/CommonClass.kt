package com.grocery.groceryapp.Utils

import android.annotation.SuppressLint
import androidx.compose.foundation.background
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.grocery.groceryapp.R
import com.grocery.groceryapp.ui.theme.*

@Composable
fun Text14_400(text: String, color: Color = bodyTextColor, modifier: Modifier = Modifier) {
    Text(text = text, style = loginTypography.body1, color = color, modifier = modifier)
}

@Composable
fun Text24_700(text: String, color: Color = titleColor, modifier: Modifier = Modifier) {
    Text(text = text, style = loginTypography.h1, color = color, modifier = modifier,)
}

@Composable
fun Text20_700(text: String, color: Color = titleColor, modifier: Modifier = Modifier) {
    Text(text = text, style = loginTypography.h4, color = color, modifier = modifier)
}

@Composable
fun Text16_700(text: String, color: Color = blackColor, modifier: Modifier = Modifier) {
    Text(text = text, style = loginTypography.body2, color = color, modifier = modifier,)
}


@Composable
fun Text18_600(text: String, color: Color = headingColor, modifier: Modifier = Modifier) {
    Text(text = text, style = loginTypography.h2, color = color, modifier = modifier)
}
@Composable
fun CommonButton(
    text: String,
    color: Color = Color.White,
    backgroundColor: Color = titleColor,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp)
    ) {
        Box(
            modifier = Modifier
                .background(backgroundColor)
                .clickable { onClick() }
        ) {
            Text16_700(
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
fun CommonHeader(text: String, onClick: () -> Unit = {}) {
    Row {
        IconButton(
            onClick = { onClick() }
        ) {
            Icon(
                Icons.Default.ArrowBack,
                contentDescription = "",
            )
        }
        Text24_700(
            text = text,
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .align(Alignment.CenterVertically)
        )
    }
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
    onClick: () -> Unit = {}

) {
    TextField(
        value = text.value,
        onValueChange = { text.value = it },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.White,
            focusedLabelColor = fadedTextColor,
            textColor = headingColor,
            unfocusedLabelColor = fadedTextColor,
            unfocusedIndicatorColor = borderColor,
            focusedIndicatorColor = headingColor,
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
            .fillMaxWidth()
            .clickable { onClick() },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        enabled = enable
    )
}
