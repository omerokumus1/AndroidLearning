package com.example.androidlearning

class RemoteDataSource(
    private val apiService: ApiService
) {
    /*    suspend fun getPosts(): List<Post> {
            return try {
                val response = apiService.getPosts()
                if (response.isSuccessful) {
                    response.body()!!
                } else {
                    emptyList()
                }
            } catch (e: Exception) {
                emptyList()
            }
        }*/

    /*suspend fun getPosts(): NetworkResult<List<Post>> {
        return try {
            val response = apiService.getPosts()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    NetworkResult.Success(body)
                } else {
                    NetworkResult.Error(response.code(), response.message())
                }
            } else {
                NetworkResult.Error(response.code(), response.message())
            }
        } catch (e: Exception) {
            NetworkResult.Exception(e)
        } catch (t: Throwable) {
            NetworkResult.Exception(t)
        }
    }*/

    suspend fun getPosts(): NetworkResult<List<Post>> = handleApi {
        apiService.getPosts()
    }

}