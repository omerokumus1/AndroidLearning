package com.example.androidlearning

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetAllPostsCallback : Callback<List<Post>> {
    override fun onResponse(
        call: Call<List<Post>>,
        response: Response<List<Post>>
    ) {
        TODO("Not yet implemented")
    }

    override fun onFailure(
        call: Call<List<Post>>,
        t: Throwable
    ) {
        TODO("Not yet implemented")
    }
}