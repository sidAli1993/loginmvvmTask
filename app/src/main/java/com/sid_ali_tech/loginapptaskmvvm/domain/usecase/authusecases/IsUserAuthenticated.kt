package com.sid_ali_tech.loginapptaskmvvm.domain.usecase.authusecases

import com.sid_ali_tech.loginapptaskmvvm.domain.repository.AuthRepository
import javax.inject.Inject

class IsUserAuthenticated @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke()=authRepository.isUserAuthenticated()

}