package com.grocery.groceryapp.common

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform

sealed class ApiState<out T> {

    data class Success<out R>(val data: R) : ApiState<R>()
    data class Failure(val msg:Throwable) : ApiState<Nothing>()
    object Loading : ApiState<Nothing>()

}

fun <T> Flow<ApiState<T>>.doOnSuccess(action: suspend (T) -> Unit): Flow<ApiState<T>> =
    transform { result ->
        if (result is ApiState.Success) {
            action(result.data)
        }
        return@transform emit(result)
    }

fun <T> Flow<ApiState<T>>.doOnFailure(action: suspend (Throwable?) -> Unit): Flow<ApiState<T>> =
    transform { result ->
        if (result is ApiState.Failure) {
            action(result.msg)
        }
        return@transform emit(result)
    }

fun <T> Flow<ApiState<T>>.doOnLoading(action: suspend () -> Unit): Flow<ApiState<T>> =
    transform { result ->
        if (result is ApiState.Loading) {
            action()
        }
        return@transform emit(result)
    }