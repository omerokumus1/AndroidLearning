package com.example.androidlearning.data

import com.example.androidlearning.data.datasource.User
import com.example.androidlearning.data.datasource.UserLocalDataSource

//* Single Type Exposing (Reactive Streams)
class UserRepository4(private val userLocalDataSource: UserLocalDataSource) {

    // Create or Update a user
    fun saveUser(user: User): Single<Boolean> {
        return userLocalDataSource.saveUser(user)
    }

    // Read a user by ID
    fun getUserById(userId: Int): Single<User> {
        return userLocalDataSource.getUserByID(userId)
    }
}


class Single<T>