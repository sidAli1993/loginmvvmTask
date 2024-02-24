package com.sid_ali_tech.loginapptaskmvvm.domain.usecase.authusecases

import com.sid_ali_tech.loginapptaskmvvm.domain.repository.AuthRepository
import javax.inject.Inject

class SignIn @Inject constructor(private val authRepository: AuthRepository) {
    operator fun invoke(email:String,password:String)=authRepository.firebaseSignIn(email ,password)
}