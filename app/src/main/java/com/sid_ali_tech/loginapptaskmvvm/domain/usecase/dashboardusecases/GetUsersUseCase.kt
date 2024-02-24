package com.sid_ali_tech.loginapptaskmvvm.domain.usecase.dashboardusecases

import com.sid_ali_tech.loginapptaskmvvm.data.model.User
import com.sid_ali_tech.loginapptaskmvvm.domain.repository.GetUsersRepository
import com.sid_ali_tech.loginapptaskmvvm.utils.Response
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(private val getUsersRepository: GetUsersRepository) {
    operator fun  invoke(): Flow<Response<List<User>>> {
        return getUsersRepository.getUsersList()
    }
}