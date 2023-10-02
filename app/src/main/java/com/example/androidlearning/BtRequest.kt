package com.example.androidlearning

import com.google.gson.Gson

data class BtRequest(val kullanici: String, val komut: String, val rol: String) {

    fun prepareToSend(): String {
        val json = Gson().toJson(this)
        return "bas[$json]son"
    }
}
