package com.grocery.groceryapp.connectionState

sealed class ConnectionState{
    object Available : ConnectionState()
    object Unavailable : ConnectionState()
}
