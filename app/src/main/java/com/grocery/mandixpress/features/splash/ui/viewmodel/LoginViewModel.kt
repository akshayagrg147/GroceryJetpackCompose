package com.grocery.mandixpress.features.splash.ui.viewmodel

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.grocery.mandixpress.Utils.showLog
import com.grocery.mandixpress.common.ApiState
import com.grocery.mandixpress.data.modal.CheckNumberExistResponse
import com.grocery.mandixpress.data.modal.RegisterLoginRequest
import com.grocery.mandixpress.features.home.ui.viewmodal.ComposeUiResponse
import com.grocery.mandixpress.features.splash.domain.repository.AuthRepository
import com.grocery.mandixpress.features.splash.domain.repository.CommonRepository
import com.grocery.mandixpress.SharedPreference.CombinedSharedPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repo:AuthRepository, private val commonRepository: CommonRepository, private val sharedpreference: CombinedSharedPreference,
) : ViewModel(){
    private val _loginResponse:MutableStateFlow<ComposeUiResponse<CheckNumberExistResponse>> = MutableStateFlow(
        ComposeUiResponse()
    )
     val loginResponse=_loginResponse.asStateFlow()


    fun createUserWithPhone(
        mobile:String,
        activity: Activity
    ) = repo.createUserWithPhone(mobile,activity)

fun saveBasicInformation(
    mobileRegisterResponse: ComposeUiResponse<CheckNumberExistResponse>,
    mobile: String,
    pincode: String
) {
    sharedpreference.setJwtToken(mobileRegisterResponse.data?.jwtToken?:"")
    sharedpreference.setMobileNumber("+91${mobile}")
    sharedpreference.setPinCode(pincode)
}

    fun signWithCredential(
        code:String
    ) = repo.signInWithCredentials(code)
fun onEvent(mobile: LoginEvent) {
    when(mobile){
      is  LoginEvent.LoginResponse->{
            viewModelScope.launch {
                commonRepository.checkMobileNumberExist(
                    RegisterLoginRequest(null,null,mobile.mobileNumber)
                ) .collectLatest {
                    when(it){
                        is ApiState.Success->{
                            showLog("loginViewModal", "checkMobileNumberExist 11: ${Gson().toJson(it.data)}")
                            _loginResponse.emit(ComposeUiResponse(data = it.data))

                        }
                        is ApiState.Failure->{
                            _loginResponse.emit(ComposeUiResponse(error = it.msg.toString()))
                            showLog("loginViewModal", "checkMobileNumberExist22: ${it.msg}")
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
    fun showmsg(message: String) {
        showLog("showinggetfcmtoken","$message")

    }
}
sealed class LoginEvent{
    data class LoginResponse(val mobileNumber:String): LoginEvent()
}