package com.example.androidlearning.data

import com.example.androidlearning.data.datasource.ApiCallback
import com.example.androidlearning.data.datasource.User
import com.example.androidlearning.data.datasource.UserLocalDataSource

//* Functions with Callbacks Exposing
class UserRepository3(private val userLocalDataSource: UserLocalDataSource) {

    // Create or Update a user
    fun saveUser(user: User, callback: (Result<Boolean>) -> Unit) {
        userLocalDataSource.saveUser(user, object : ApiCallback<Unit> {
            override fun onSuccess(result: Unit) {
                callback(Result.success(true))
            }

            override fun onFailure(error: Throwable) {
                callback(Result.failure(error))
            }
        })
    }

    // Read a user by ID
    fun getUserById(userId: Int, callback: (Result<User>) -> Unit) {
        userLocalDataSource.getUserById(userId, object : ApiCallback<User> {
            override fun onSuccess(result: User) {
                callback(Result.success(result))
            }

            override fun onFailure(error: Throwable) {
                callback(Result.failure(error))
            }
        })
    }
}

