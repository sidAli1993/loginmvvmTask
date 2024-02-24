package com.sid_ali_tech.loginapptaskmvvm.domain.repository

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.sid_ali_tech.loginapptaskmvvm.data.model.User
import com.sid_ali_tech.loginapptaskmvvm.utils.Response
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun isUserAuthenticated(): Boolean

    fun getFirebaseAuthState(): Flow<Boolean>

    fun firebaseSignIn(email: String, password: String): Flow<Response<Boolean>>
    fun forgetPassword(email: String): Flow<Response<Boolean>>

    fun firebaseSignOut(): Flow<Response<Boolean>>

    fun firebaseSignUp(email: String, password: String, user: User): Flow<Response<Boolean>>


}