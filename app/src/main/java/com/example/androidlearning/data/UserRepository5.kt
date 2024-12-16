package com.example.androidlearning.data

import com.example.androidlearning.data.datasource.User
import com.example.androidlearning.data.datasource.UserLocalDataSource

//* Maybe Type Exposing (Reactive Streams)
class UserRepository5(private val userLocalDataSource: UserLocalDataSource) {
    // Read a user by ID
    fun getUserById(userId: Int): Maybe<User> {
        return userLocalDataSource.getUser(userId)
    }
}

class Maybe<T>
