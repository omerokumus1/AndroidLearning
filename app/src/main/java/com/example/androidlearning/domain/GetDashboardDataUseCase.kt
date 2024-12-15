package com.example.androidlearning.domain

import com.example.androidlearning.data.DashboardRepository


class GetDashboardDataUseCase(private val dashboardRepository: DashboardRepository) {
    suspend operator fun invoke(): DashboardData {
        return dashboardRepository.getDashboardData()
    }
}
