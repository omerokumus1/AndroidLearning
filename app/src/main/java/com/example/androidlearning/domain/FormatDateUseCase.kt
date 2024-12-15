package com.example.androidlearning.domain

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

//* Simple Delegate for ViewModel
class FormatDateUseCase {

    private val formatter = SimpleDateFormat(
        "dd/MM/yyyy",
        Locale.ENGLISH
    )

    fun execute(date: Date): String {
        return formatter.format(date)
    }

    fun execute(dateStr: String): String {
        return formatter.format(Date(dateStr))
    }


}
