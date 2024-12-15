package com.example.androidlearning.presentation

import androidx.lifecycle.ViewModel
import com.example.androidlearning.domain.FormatDateUseCase
import java.util.Date

class MainViewModel : ViewModel() {
    private val formatDateUseCase = FormatDateUseCase()

    fun formatDate(date: String): String {
        return formatDateUseCase.execute(Date(date))
    }
}