package com.example.androidlearning

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class MainViewModel: ViewModel() {
    private val baseUrl = "https://jsonplaceholder.typicode.com/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService: ApiService by lazy {
        retrofit.create()
    }

    fun getFirstPost() {
        val call = apiService.getFirstPost()
        call.enqueue(object: Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                if (response.isSuccessful) {
                    val post = response.body()
                    val statusCode = response.code()
                } else {
                    val error = response.errorBody()
                    val message = response.message()
                }
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                Log.d("response", "error: $t")
            }
        })
    }

    fun getSecondPost() {
        viewModelScope.launch {
            val response = apiService.getSecondPost()
            if (response.isSuccessful) {
                val post = response.body()
                val statusCode = response.code()
            } else {
                val error = response.errorBody()
                val message = response.message()
            }
        }
    }
}