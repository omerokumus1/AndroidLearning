package com.example.androidlearning

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.androidlearning.data.AuthRepository
import kotlinx.coroutines.launch

class MainViewModel(private val authRepository: AuthRepository) : ViewModel() {

    companion object {
        val AUTH_REPO_DI_KEY = object : CreationExtras.Key<AuthRepository> {}
    }

    fun login() {
        viewModelScope.launch {
            authRepository.login("test@test.com", "test123")
        }
    }

}

//* This is the factory that will instantiate the MainViewModel
class MainViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(MyApp.appModule.authRepository) as T
    }
}

//* You can pass the dependencies for ViewModel to the ViewModelFactory
class MainViewModelFactory2(private val authRepository: AuthRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(authRepository) as T
    }
}

//* You can pass the dependencies for ViewModel to the ViewModelFactory via CreationExtras
class MainViewModelFactory3() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        val authRepository = extras[MainViewModel.AUTH_REPO_DI_KEY] ?: error("AuthRepository is required")
        return MainViewModel(authRepository) as T
    }
}

//* Or you can use a simple helper function
fun <VM: ViewModel> viewModelFactory(initializer: () -> VM) = object : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return initializer() as T
    }
}