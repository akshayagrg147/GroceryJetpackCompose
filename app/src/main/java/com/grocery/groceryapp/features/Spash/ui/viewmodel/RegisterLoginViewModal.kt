package com.grocery.groceryapp.features.Spash.ui.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.grocery.groceryapp.features.Spash.domain.repository.CommonRepository
import com.grocery.groceryapp.data.modal.FailureResponse
import com.grocery.groceryapp.data.modal.RegisterLoginRequest
import com.grocery.groceryapp.data.modal.RegisterLoginResponse
import com.grocery.groceryapp.common.ApiState
import com.squareup.moshi.Moshi
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterLoginViewModal @Inject constructor(private val repository: CommonRepository, private val moshi: Moshi,@ApplicationContext applicationContext: Context) : ViewModel() {

    init {
        Places.initialize(applicationContext, "AIzaSyBmYp6mt0CNOiZALzbN10jwBxgN6n2E8-U")
    }

    val field = listOf(Place.Field.NAME, Place.Field.LAT_LNG)
     private val registrationResponse: MutableState<RegisterLoginResponse> = mutableStateOf(
         RegisterLoginResponse()
     )
    val registrationGetResponse: State<RegisterLoginResponse> = registrationResponse
   // fun register(registerLoginResponse: RegisterLoginRequest) = repository.registerUser(registerLoginResponse)

    val _res:MutableSharedFlow<RegisterLoginRequest> = MutableSharedFlow(1)

    fun setData(data: RegisterLoginRequest){
        _res.tryEmit(data)
    }


    fun getRegistration() = viewModelScope.launch {
        repository.registerUser(_res.first()).collect {
            when (it) {
                is ApiState.Success -> {
                    registrationResponse.value = RegisterLoginResponse(it.data.message, it.data.statusCode, it.data.token,it.data.status)
                }
                is ApiState.Failure -> {
                    registrationResponse.value = RegisterLoginResponse("Something went wrong", 400, null,false)
                }
                ApiState.Loading -> {

                }
            }
        }
    }
     fun getLoginResponse() = viewModelScope.launch {
        repository.registerUser(_res.first()).collect {
            when (it) {
                is ApiState.Success -> {
                    registrationResponse.value = RegisterLoginResponse(it.data.message, it.data.statusCode, null,it.data.status)
                }
                is ApiState.Failure -> {
                    val adapter = moshi.adapter<FailureResponse>(FailureResponse::class.java)
                    val data = adapter.fromJson(it.msg.toString())
                    registrationResponse.value = RegisterLoginResponse(data?.message, 400, null,false)
                }
                ApiState.Loading -> {

                }
            }
        }
    }

}

