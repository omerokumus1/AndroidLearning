package com.example.androidlearning

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

// * ViewModel to store View state
class MainViewModel : ViewModel() {

    // * UI Logic State Holder created in the UI
    private var inputHandler = UserInputHandler()

    // * LiveData to observe the state of the UI
    private val _isInputValid = MutableLiveData<Boolean>()

    // * Expose LiveData to the UI
    val isInputValid: LiveData<Boolean> get() = _isInputValid

    fun setInputText(text: String) {
        inputHandler.setInputText(text)
    }

    fun validateInput() {
        _isInputValid.value = inputHandler.isInputValid()
    }

}