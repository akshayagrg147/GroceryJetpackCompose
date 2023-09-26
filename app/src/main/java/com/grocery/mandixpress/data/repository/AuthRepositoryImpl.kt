package com.grocery.mandixpress.data.repository

import android.app.Activity
import android.util.Log
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.grocery.mandixpress.common.ApiState
import com.grocery.mandixpress.features.Spash.domain.repository.AuthRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val auth:FirebaseAuth
) : AuthRepository {

    private lateinit var mVerificationCode:String

    override fun createUserWithPhone(mobile: String, activity: Activity): Flow<ApiState<String>> = callbackFlow {
       trySend(ApiState.Loading)

        val verificationCallback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){

            override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                Log.d("errormessageotp",p0.smsCode?:"verification complste")
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                Log.d("errormessageotp",p0.message?:"not know")
                trySend(ApiState.Failure(p0))
            }

            override fun onCodeSent(verificationCode: String, p1: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(verificationCode, p1)
                trySend(ApiState.Success("Otp Sent"))
                mVerificationCode = verificationCode
            }

        }
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(mobile) // Phone number to verify
            .setTimeout(30L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(activity) // Activity (for callback binding)
            .setCallbacks(verificationCallback)
            // OnVerificationStateChangedCallbacks
            .build()


        PhoneAuthProvider.verifyPhoneNumber(options)

        awaitClose {
            close()
        }
    }

    override fun signInWithCredentials(code: String): Flow<ApiState<String>> = callbackFlow{
        val credential = PhoneAuthProvider.getCredential(mVerificationCode,code)
        trySend(ApiState.Loading)

        auth.signInWithCredential(credential)
            .addOnCompleteListener {
                if(it.isSuccessful)
                    trySend(ApiState.Success("OTP Verified"))
            }.addOnFailureListener {
                trySend(ApiState.Failure(it))
            }
        awaitClose {
            close()
        }
    }


}