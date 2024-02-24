package com.sid_ali_tech.loginapptaskmvvm.domain.usecase

import com.sid_ali_tech.loginapptaskmvvm.domain.usecase.dashboardusecases.GetUsersUseCase


data class DashboardUseCases(
    val getUsersUseCase: GetUsersUseCase,
)