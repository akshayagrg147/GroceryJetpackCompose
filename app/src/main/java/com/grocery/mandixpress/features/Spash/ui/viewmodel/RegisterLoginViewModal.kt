package com.grocery.mandixpress.features.Spash.ui.viewmodel

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.grocery.mandixpress.SharedPreference.sharedpreferenceCommon
import com.grocery.mandixpress.features.Spash.domain.repository.CommonRepository
import com.grocery.mandixpress.data.modal.RegisterLoginRequest
import com.grocery.mandixpress.data.modal.RegisterLoginResponse
import com.grocery.mandixpress.common.ApiState
import com.squareup.moshi.Moshi
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterLoginViewModal @Inject constructor(private val repository: CommonRepository, private val shared: sharedpreferenceCommon,@ApplicationContext applicationContext: Context) : ViewModel() {

    init {
        Places.initialize(applicationContext, "AIzaSyBmYp6mt0CNOiZALzbN10jwBxgN6n2E8-U")
    }

    val field = listOf(Place.Field.NAME, Place.Field.LAT_LNG)
    private val _registerEventFlow:MutableSharedFlow<ApiState<RegisterLoginResponse>> = MutableSharedFlow()
    var registerEventFlow = _registerEventFlow.asSharedFlow()
    private set

    fun onEvent(event: RegisterEvent){
        when(event){
            is RegisterEvent.RegisterEventFlow -> viewModelScope.launch {
                repository.registerUser(event.data)
                    .onStart {
                        _registerEventFlow.emit(ApiState.Loading)
                    }
                    .catch {
                        _registerEventFlow.emit(ApiState.Failure(it))
                    }
                    .collect{
                        _registerEventFlow.emit(it)
                    }
            }
        }
    }




}

sealed class RegisterEvent{
    data class RegisterEventFlow(val data:RegisterLoginRequest) : RegisterEvent()

}
