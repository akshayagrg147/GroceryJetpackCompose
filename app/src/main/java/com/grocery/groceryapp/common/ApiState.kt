package com.grocery.groceryapp.common

sealed class ApiState<out T> {

    data class Success<out R>(val data: R) : ApiState<R>()
    data class Failure(val msg:Throwable) : ApiState<Nothing>()
    object Loading : ApiState<Nothing>()

}