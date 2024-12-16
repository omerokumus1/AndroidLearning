package com.example.androidlearning.data

import com.example.androidlearning.data.datasource.UserLocalDataSource

//* Completable Type Exposing (Reactive Streams)
class UserRepository6(private val userLocalDataSource: UserLocalDataSource) {
    // Read a user by ID
    fun deleteUserByID(userId: Int): Completable {
        return userLocalDataSource.deleteUserByID(userId)
    }
}

class Completable