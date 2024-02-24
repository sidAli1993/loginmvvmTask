package com.sid_ali_tech.loginapptaskmvvm.domain.usecase

import com.sid_ali_tech.loginapptaskmvvm.domain.usecase.authusecases.AuthState
import com.sid_ali_tech.loginapptaskmvvm.domain.usecase.authusecases.ForgetPassword
import com.sid_ali_tech.loginapptaskmvvm.domain.usecase.authusecases.IsUserAuthenticated
import com.sid_ali_tech.loginapptaskmvvm.domain.usecase.authusecases.SignIn
import com.sid_ali_tech.loginapptaskmvvm.domain.usecase.authusecases.SignOut
import com.sid_ali_tech.loginapptaskmvvm.domain.usecase.authusecases.SignUp


data class AuthUseCases(
    val authState: AuthState,
    val isUserAuthenticated: IsUserAuthenticated,
    val signIn: SignIn,
    val signOut: SignOut,
    val signUp: SignUp,
    val forgetPassword: ForgetPassword
)
