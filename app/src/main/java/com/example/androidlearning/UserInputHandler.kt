package com.example.androidlearning

// Plain UI Logic State Holder class
class UserInputHandler {

    private var inputText: String = ""

    fun setInputText(text: String) {
        inputText = text
    }

    fun getInputText(): String {
        return inputText
    }

    // Example of simple validation logic
    fun isInputValid(): Boolean {
        return inputText.isNotEmpty()
    }

    fun clearInput() {
        inputText = ""
    }
}
