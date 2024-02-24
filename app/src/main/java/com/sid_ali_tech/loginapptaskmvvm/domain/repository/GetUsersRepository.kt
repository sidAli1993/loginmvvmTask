package com.sid_ali_tech.loginapptaskmvvm.domain.repository

import com.sid_ali_tech.loginapptaskmvvm.data.model.User
import com.sid_ali_tech.loginapptaskmvvm.utils.Response
import kotlinx.coroutines.flow.Flow

interface GetUsersRepository {
    fun getUsersList(): Flow<Response<List<User>>>
}