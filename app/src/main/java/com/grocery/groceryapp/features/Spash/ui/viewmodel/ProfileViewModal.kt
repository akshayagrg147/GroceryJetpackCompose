package com.grocery.groceryapp.features.Spash.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grocery.groceryapp.RoomDatabase.CartItems
import com.grocery.groceryapp.RoomDatabase.Dao
import com.grocery.groceryapp.SharedPreference.sharedpreferenceCommon
import com.grocery.groceryapp.common.ApiState
import com.grocery.groceryapp.data.modal.ProductByIdResponseModal
import com.grocery.groceryapp.data.modal.ProductIdIdModal
import com.grocery.groceryapp.data.modal.UserResponse
import com.grocery.groceryapp.features.Spash.domain.repository.CommonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModal @Inject constructor(val repository: CommonRepository, val dao: Dao,val shared: sharedpreferenceCommon):ViewModel() {
    private val live:MutableState<UserResponse> = mutableStateOf(UserResponse(null,null,null,null))
    val responseLiveData:MutableState<UserResponse> =live



    fun callingUserDetails()=viewModelScope.launch {

        repository.getUserResponse("+919812205054").collectLatest {
            when(it){
                is ApiState.Success->{

                    live.value=it.data

                }
                is ApiState.Failure->{
                    live.value= UserResponse(it.msg.message,null,401,null)

                }
                is ApiState.Loading->{

                }

            }
        }

    }


}