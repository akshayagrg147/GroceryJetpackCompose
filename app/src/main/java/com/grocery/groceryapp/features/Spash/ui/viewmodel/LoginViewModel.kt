package com.grocery.groceryapp.features.Spash.ui.viewmodel

import android.app.Activity
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grocery.groceryapp.common.ApiState
import com.grocery.groceryapp.data.modal.CheckNumberExistResponse
import com.grocery.groceryapp.data.modal.RegisterLoginRequest
import com.grocery.groceryapp.features.Spash.domain.repository.AuthRepository
import com.grocery.groceryapp.features.Spash.domain.repository.CommonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repo:AuthRepository,private val commonRepository: CommonRepository
) : ViewModel(){
    private val response:MutableState<CheckNumberExistResponse> = mutableStateOf(
        CheckNumberExistResponse(false,null,null,null)
    )
    val resp=response


    fun createUserWithPhone(
        mobile:String,
        activity: Activity
    ) = repo.createUserWithPhone(mobile,activity)

    fun signWithCredential(
        code:String
    ) = repo.signInWithCredentials(code)

    fun checkMobileNumberExist(mobile: String)=viewModelScope.launch {
        commonRepository.checkMobileNumberExist(
            RegisterLoginRequest(null,null,mobile)
        ) .collectLatest {
            when(it){
                is ApiState.Success->{
                    response.value=it.data

                }
                is ApiState.Failure->{
                    response.value=CheckNumberExistResponse(false,null,false,401)
                }
                is ApiState.Loading->{

                }
            }
        }
    }

    fun gettingJwtToken()=commonRepository.gettingJwt()
}