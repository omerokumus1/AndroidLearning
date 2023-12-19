package com.example.androidlearning

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query


interface ApiService {
    @GET("posts")
    suspend fun getPosts(): Response<List<Post>>


    @PUT("posts/1")
    suspend fun putFirstPost(@Body post: Post): Response<Post>

    @PATCH("posts/1")
    suspend fun patchFirstPost(@Body post: Post): Post

    @DELETE("posts/1")
    suspend fun deleteFirstPost()


    @DELETE("posts")
    suspend fun deletePostById(@Query("id") id: Int)


    @POST("posts")
    suspend fun postPost(@Body post: Post): Post

    @GET("posts/1")
    fun getFirstPost(): Call<Post>

    @GET("posts/2")
    fun getAllPosts(): Call<List<Post>>

    @GET("posts/2")
    suspend fun getSecondPost(): Response<Post>



//    @GET("posts/{id}/comments")
//    suspend fun getPostById(@Path("id") id: Int): Post

    @GET("posts")
    suspend fun getPostById(@Query("id") id: Int): Post

}