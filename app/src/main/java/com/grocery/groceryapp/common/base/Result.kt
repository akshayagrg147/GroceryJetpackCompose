package com.grocery.groceryapp

import com.grocery.groceryapp.common.ApiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import java.io.IOException


fun <T> toResultFlow(call: suspend () -> Response<T>): Flow<ApiState<T>> = flow {

    emit(ApiState.Loading)

    try {
        val response = call()

        if (response.isSuccessful) {
            response.body()?.let {
                emit(ApiState.Success(it))
            }
        } else {
            response.errorBody()?.let { error ->
                error.close()
                emit(ApiState.Failure(Throwable(error.toString())))
            }
        }

    } catch (e: Exception) {
        emit(ApiState.Failure(IOException(e.message)))
    }

}.flowOn(Dispatchers.IO)