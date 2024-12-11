package com.example.androidlearning

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

//* Repository as SSOT
class UserRepository(private val apiService: ApiService) {

    //* Expose a Flow as an immutable data source
    suspend fun getUser(userId: Int): Flow<User> {
        return  MutableStateFlow(apiService.getUser(userId))
    }

}