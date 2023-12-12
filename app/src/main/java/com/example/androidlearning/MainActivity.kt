package com.example.androidlearning

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.androidlearning.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val baseUrl = "https://jsonplaceholder.typicode.com/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService: ApiService by lazy {
        retrofit.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val apiService = retrofit.create(ApiService::class.java)

//        CoroutineScope(Dispatchers.IO).launch {
//            val firstPost = apiService.getFirstPost()
//            Log.d("response", "$firstPost")
//        }
//
//        CoroutineScope(Dispatchers.IO).launch {
//            val posts = apiService.getPosts()
//            Log.d("response", "$posts")
//        }

        CoroutineScope(Dispatchers.IO).launch {
            val post = Post(300, 301, "Title", "Body")
            val response = apiService.postPost(post)
            Log.d("response", "$response")
        }

        CoroutineScope(Dispatchers.IO).launch {
            val post = Post(1, 1, "Title", "Body")
            val response = apiService.putFirstPost(post)
            Log.d("response", "$response")
        }

        CoroutineScope(Dispatchers.IO).launch {
            val post = Post(1, 1, "Title New", "Body")
            val response = apiService.patchFirstPost(post)
            Log.d("response", "$response")
        }

        CoroutineScope(Dispatchers.IO).launch {
            apiService.deleteFirstPost()
        }





    }
}


interface ApiService {
    @PUT("posts/1")
    suspend fun putFirstPost(@Body post: Post): Post

    @PATCH("posts/1")
    suspend fun patchFirstPost(@Body post: Post): Post

    @DELETE("posts/1")
    suspend fun deleteFirstPost()


    @DELETE("posts")
    suspend fun deletePostById(@Query("id") id: Int)


    @POST("posts")
    suspend fun postPost(@Body post: Post): Post

    @GET("posts/1")
    suspend fun getFirstPost(): Post

    @GET("posts")
    suspend fun getPosts(): List<Post>

//    @GET("posts/{id}/comments")
//    suspend fun getPostById(@Path("id") id: Int): Post

    @GET("posts")
    suspend fun getPostById(@Query("id") id: Int): Post





}


data class Post(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)

