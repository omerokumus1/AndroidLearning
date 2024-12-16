package com.example.androidlearning.data

import com.example.androidlearning.data.datasource.User
import com.example.androidlearning.data.datasource.UserLocalDataSource

//* suspend function Exposing
class UserRepository2(private val userLocalDataSource: UserLocalDataSource) {

    // Create or Update a user
    suspend fun saveUser(user: User) {
        userLocalDataSource.insertOrUpdateUser(user)
    }

    // Read a user by ID
    suspend fun getUserById(userId: Int): User {
        return userLocalDataSource.getUserById(userId)
    }

    // Delete a user by ID
    suspend fun deleteUserById(userId: Int) {
        userLocalDataSource.deleteUserById(userId)
    }
}

