package com.example.androidlearning.data

class UserRepository {
    fun getCurrencyMap(): Map<String, Double> {
        return mapOf(
            "USD" to 1.0,
            "EUR" to 0.85,
            "JPY" to 110.0,
            "GBP" to 0.73,
            "AUD" to 1.35,
            "CAD" to 1.25,
            "CHF" to 0.92,
            "CNY" to 6.45,
            "SEK" to 8.55,
            "NZD" to 1.45
        )

    }

}