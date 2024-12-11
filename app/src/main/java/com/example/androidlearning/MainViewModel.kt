package com.example.androidlearning

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

//* ViewModel as SSOT
class MainViewModel : ViewModel() {

    //* Expose immutable LiveData to the UI
    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    fun loadUser(userId: Int) {
        viewModelScope.launch {
            //* Fetch from the network
            val fetchedUser = RetrofitClient.apiService.getUser(userId)
            _user.value = fetchedUser
        }
    }

}
