package com.sid_ali_tech.loginapptaskmvvm.domain.usecase.authusecases

import com.sid_ali_tech.loginapptaskmvvm.data.model.User
import com.sid_ali_tech.loginapptaskmvvm.domain.repository.AuthRepository
import com.sid_ali_tech.loginapptaskmvvm.utils.Response
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignUp @Inject constructor(private val authRepository: AuthRepository) {
    operator fun invoke(email: String, password: String, user: User): Flow<Response<Boolean>> {
        return authRepository.firebaseSignUp(email, password, user)
    }
}