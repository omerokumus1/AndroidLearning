package com.example.androidlearning.di

import com.example.androidlearning.data.AuthApi
import com.example.androidlearning.data.AuthRepository

//* This is the interface that will provide the dependencies for the app
//* You can have multiple interfaces like this for different parts of the app
//* You can also have multiple implementations of this interface for different build variants
//* This interface helps you to follow programming to an interface principle, thus, you can
//* easily switch the implementation of the interface
interface AppModule {
    val authApi: AuthApi
    val authRepository: AuthRepository
}