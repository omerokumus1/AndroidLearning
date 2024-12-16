package com.example.androidlearning.data.datasource

import com.example.androidlearning.data.Maybe
import com.example.androidlearning.data.Single

class UserLocalDataSource {
    //* suspend function Exposing
    suspend fun insertOrUpdateUser(user: User) {
        // Insert or Update user in local database
    }

    suspend fun getUserById(userId: Int): User {
        // Get user by ID from local database
        return User(1, "John Doe", 25)
    }

    suspend fun deleteUserById(userId: Int) {
        // Delete user by ID from local database
    }

    //* Functions with Callbacks Exposing
    fun saveUser(user: User, callback: ApiCallback<Unit>) {
        // Insert or Update user in local database
        callback.onSuccess(Unit)
    }

    fun getUserById(userId: Int, callback: ApiCallback<User>) {
        // Get user by ID from local database
        callback.onSuccess(User(1, "John Doe", 25))
    }

    //* Single Type Exposing (Reactive Streams)
    fun saveUser(user: User): Single<Boolean> {
        // Insert or Update user in local database
        TODO()
    }

    fun getUserByID(userId: Int): Single<User> {
        // Get user by ID from local database
        TODO()
    }

    //* Maybe Type Exposing (Reactive Streams)
    fun getUser(userId: Int): Maybe<User> {
        // Get user by ID from local database
        TODO()
    }
}

class User(val id: Int, val name: String, val age: Int)

interface ApiCallback<T> {
    fun onSuccess(result: T)
    fun onFailure(error: Throwable)
}