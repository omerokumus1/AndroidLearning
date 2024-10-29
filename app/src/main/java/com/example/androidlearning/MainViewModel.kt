package com.example.androidlearning

import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    // * ViewModel to store View state
    var inputText: String = ""
    private set

    fun setInputText(text: String) {
        inputText = text
    }

    fun clearInput() {
        inputText = ""
    }

}