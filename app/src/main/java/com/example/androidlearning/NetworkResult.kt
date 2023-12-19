package com.example.androidlearning

import retrofit2.Response

sealed class NetworkResult<T: Any> {
    data class Success<T: Any>(val data: T) : NetworkResult<T>()
    data class Error<T: Any>(val code: Int, val message: String?) : NetworkResult<T>()
    data class Exception<T: Any>(val e: Throwable) : NetworkResult<T>()
}

suspend fun <T: Any> handleApi(
    execute: suspend () -> Response<T>
): NetworkResult<T> {
    return try {
        val response = execute()
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                NetworkResult.Success(body)
            } else {
                NetworkResult.Error(response.code(), response.message())
            }
        } else {
            NetworkResult.Error(response.code(), response.message())
        }
    } catch (e: Exception) {
        NetworkResult.Exception(e)
    } catch (t: Throwable) {
        NetworkResult.Exception(t)
    }
}