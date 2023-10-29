package com.grocery.mandixpress.features.home.domain.modal

import androidx.compose.ui.graphics.Color

data class MainProducts(val name:String, val image:Int, val color: Color, val productId: String?=null)
data class FilterOptions(val name:String,val productId: String?=null)
