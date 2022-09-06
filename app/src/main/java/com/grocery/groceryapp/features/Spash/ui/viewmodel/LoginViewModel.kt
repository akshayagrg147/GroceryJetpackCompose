package com.grocery.groceryapp.features.Spash.ui.viewmodel

import android.app.Activity
import androidx.lifecycle.ViewModel
import com.grocery.groceryapp.features.Spash.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repo:AuthRepository
) : ViewModel(){


    fun createUserWithPhone(
        mobile:String,
        activity: Activity
    ) = repo.createUserWithPhone(mobile,activity)

    fun signWithCredential(
        code:String
    ) = repo.signInWithCredentials(code)
}