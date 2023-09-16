package com.grocery.mandixpress.features.Spash.ui.viewmodel

import android.app.Activity
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.grocery.mandixpress.common.ApiState
import com.grocery.mandixpress.data.modal.CheckNumberExistResponse
import com.grocery.mandixpress.data.modal.RegisterLoginRequest
import com.grocery.mandixpress.features.Home.ui.viewmodal.ComposeUiResponse
import com.grocery.mandixpress.features.Spash.domain.repository.AuthRepository
import com.grocery.mandixpress.features.Spash.domain.repository.CommonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repo:AuthRepository,private val commonRepository: CommonRepository
) : ViewModel(){
    private val _loginResponse:MutableStateFlow<ComposeUiResponse<CheckNumberExistResponse>> = MutableStateFlow(
        ComposeUiResponse()
    )
     val loginResponse=_loginResponse.asStateFlow()


    fun createUserWithPhone(
        mobile:String,
        activity: Activity
    ) = repo.createUserWithPhone(mobile,activity)

    fun signWithCredential(
        code:String
    ) = repo.signInWithCredentials(code)
fun onEvent(mobile: LoginEvent) {
    when(mobile){
      is  LoginEvent.LoginResponse->{
            viewModelScope.launch {
                Log.d("loginViewModal", "checkMobileNumberExist: test11")
                commonRepository.checkMobileNumberExist(
                    RegisterLoginRequest(null,null,mobile.mobileNumber)
                ) .collectLatest {
                    when(it){
                        is ApiState.Success->{
                            Log.d("loginViewModal", "checkMobileNumberExist 11: ${Gson().toJson(it.data)}")
                            _loginResponse.emit(ComposeUiResponse(data = it.data))

                        }
                        is ApiState.Failure->{
                            _loginResponse.emit(ComposeUiResponse(error = it.msg.toString()))
                            Log.d("loginViewModal", "checkMobileNumberExist22: ${it.msg}")
                        }
                        is ApiState.Loading->{
                            _loginResponse.emit(ComposeUiResponse(isLoading = true))
                        }
                    }
                }
            }

        }

    }

}


    fun gettingJwtToken()=commonRepository.gettingJwt()
}
sealed class LoginEvent{
    data class LoginResponse(val mobileNumber:String): LoginEvent()
}