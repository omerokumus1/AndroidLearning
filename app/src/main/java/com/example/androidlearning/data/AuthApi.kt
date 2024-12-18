package com.example.androidlearning.data

import retrofit2.http.GET

interface AuthApi {
    @GET("login")
    suspend fun login()
}