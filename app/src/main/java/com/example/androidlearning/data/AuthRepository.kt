package com.example.androidlearning.data

import android.util.Log

class AuthRepository(private val authApi: AuthApi) {

        suspend fun login(email: String, password: String) {
            try {
                Log.d("AuthRepository", "login")
                authApi.login()
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
}