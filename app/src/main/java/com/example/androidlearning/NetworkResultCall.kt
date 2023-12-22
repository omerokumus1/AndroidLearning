package com.example.androidlearning

import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NetworkResultCall<T : Any>(
    private val proxy: Call<T>
) : Call<NetworkResult<T>> {

    override fun enqueue(callback: Callback<NetworkResult<T>>) {
        proxy.enqueue(object: Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                val networkResult = handleApi {  }
                callback.onResponse(this@NetworkResultCall, Response.success(networkResult))
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                val networkResult = NetworkResult.Exception<T>(t)
                callback.onFailure(this@NetworkResultCall, networkResult)
            }

        }
    }

    override fun clone(): Call<NetworkResult<T>> = NetworkResultCall(proxy.clone())
    override fun execute(): Response<NetworkResult<T>> = throw NotImplementedError()
    override fun isExecuted() = proxy.isExecuted
    override fun isCanceled() = proxy.isCanceled

    override fun cancel() { proxy.cancel() }

    override fun request() = proxy.request()
    override fun timeout() = proxy.timeout()


}