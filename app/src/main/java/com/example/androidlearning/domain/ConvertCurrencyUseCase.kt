package com.example.androidlearning.domain

import com.example.androidlearning.data.UserRepository

//* UseCase which interacts with Repository
class ConvertCurrencyUseCase(userRepository: UserRepository) {

    private val currencyMap = userRepository.getCurrencyMap()

    fun execute(amount: Double, from: String, to: String): Double {
        return amount * currencyMap[to]!! / currencyMap[from]!!
    }
}


class UserStats
class UserProfile

class UserDao {
    fun getUserProfile(): UserProfile = TODO()

}
class StatsApi {
    suspend fun fetchUserStats(): UserStats = TODO()
}
class DashboardData(
    val profile: UserProfile,
    val stats: UserStats,
    val notifications: List<String>
)


