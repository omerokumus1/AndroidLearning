package com.example.androidlearning

import androidx.lifecycle.MutableLiveData
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

    val postLiveData = MutableLiveData<Post>()
    val errorLiveData = MutableLiveData<String>()

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
                    postLiveData.value = post
                } else {
                    val message = response.message()
                    errorLiveData.value = message
                }
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                errorLiveData.value = t.message
            }
        })
    }

    fun getSecondPost() {
        viewModelScope.launch {
            val response = apiService.getSecondPost()
            if (response.isSuccessful) {
                val post = response.body()
                postLiveData.value = post
            } else {
                val message = response.message()
                errorLiveData.value = message
            }
        }
    }
}