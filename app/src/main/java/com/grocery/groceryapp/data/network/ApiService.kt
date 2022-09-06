package com.grocery.groceryapp.data.network

import com.grocery.groceryapp.Utils.Constants
import com.grocery.groceryapp.data.modal.RegisterLoginRequest
import com.grocery.groceryapp.data.modal.RegisterLoginResponse
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @POST(Constants.ApiEnd_register)
    suspend fun RegisterUser(
        @Body addUser: RegisterLoginRequest,
    ): Response<RegisterLoginResponse>

    @POST(Constants.ApiEnd_login)
    suspend fun LoginUser(
        @Body addUser: String,
    ): Response<String>


}