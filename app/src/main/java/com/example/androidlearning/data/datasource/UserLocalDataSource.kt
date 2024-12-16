package com.example.androidlearning.data.datasource

class UserLocalDataSource {
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
}

class User(val id: Int, val name: String, val age: Int)
