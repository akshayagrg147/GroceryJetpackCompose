package com.grocery.mandixpress.connectionState

sealed class ConnectionState{
    object Available : ConnectionState()
    object Unavailable : ConnectionState()
}
