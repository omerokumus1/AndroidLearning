package com.example.androidlearning.data

import com.example.androidlearning.domain.DashboardData
import com.example.androidlearning.domain.StatsApi
import com.example.androidlearning.domain.UserDao

class DashboardRepository(
    private val userDao: UserDao,
    private val statsApi: StatsApi,
    private val cache: Cache
) {

    private var cachedDashboardData: DashboardData? = null

    suspend fun getDashboardData(): DashboardData {
        return cachedDashboardData ?: run {
            val profile = userDao.getUserProfile()
            val stats = statsApi.fetchUserStats()
            val notifications = cache.getNotifications()

            DashboardData(profile, stats, notifications).also { cachedDashboardData = it }
        }
    }
}