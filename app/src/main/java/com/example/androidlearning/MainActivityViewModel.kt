package com.example.androidlearning

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.random.Random

data class DiceUiState(
    val firstDieValue: Int? = null,
    val secondDieValue: Int? = null,
    val numberOfRolls: Int = 0,
)


class MainActivityViewModel : ViewModel() {

    // Expose screen UI state
    private val _uiState = MutableLiveData(DiceUiState())
    val uiState: LiveData<DiceUiState> get() = _uiState

    // Handle business logic
    fun rollDice() {
        _uiState.value =
            _uiState.value?.copy(
                firstDieValue = Random.nextInt(from = 1, until = 7),
                secondDieValue = Random.nextInt(from = 1, until = 7),
                numberOfRolls = _uiState.value?.numberOfRolls?.plus(1) ?: 1
            )
    }

    override fun onCleared() {
        super.onCleared()
    }


}

