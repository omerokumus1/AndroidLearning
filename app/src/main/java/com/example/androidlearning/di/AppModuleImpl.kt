package com.example.androidlearning.di

import android.content.Context
import com.example.androidlearning.data.AuthApi
import com.example.androidlearning.data.AuthRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppModuleImpl(private val appContext: Context) : AppModule {
    //* Normally, you create the API with retrofit
    override val authApi: AuthApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.example.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthApi::class.java)
    }
    override val authRepository: AuthRepository by lazy {
        AuthRepository(authApi)
    }
}