package com.grocery.groceryapp.features.Spash.domain.repository

import com.grocery.groceryapp.data.modal.RegisterLoginRequest
import com.grocery.groceryapp.data.network.ApiService
import com.grocery.groceryapp.toResultFlow
import javax.inject.Inject

class CommonRepository @Inject constructor(
    private val apiService: ApiService
) {

    fun registerUser(
        addUser: RegisterLoginRequest
    ) = toResultFlow {
        apiService.RegisterUser(addUser)
    }
    fun LoginUser(
        addUser: String
    ) = toResultFlow {
        apiService.LoginUser(addUser)
    }




}