package com.sid_ali_tech.loginapptaskmvvm.data.repository

import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.sid_ali_tech.loginapptaskmvvm.data.model.User
import com.sid_ali_tech.loginapptaskmvvm.domain.repository.AuthRepository
import com.sid_ali_tech.loginapptaskmvvm.utils.Constants
import com.sid_ali_tech.loginapptaskmvvm.utils.Constants.TAG
import com.sid_ali_tech.loginapptaskmvvm.utils.Response
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val fireStore: FirebaseFirestore,
) : AuthRepository {
    var operationSuccessFull: Boolean = false
    override fun isUserAuthenticated(): Boolean {
        return auth.currentUser != null
    }

    override fun getFirebaseAuthState(): Flow<Boolean> = callbackFlow {
        val authStateListener = FirebaseAuth.AuthStateListener {
            trySend(auth.currentUser == null)

        }
        auth.addAuthStateListener(authStateListener)

        awaitClose {
            auth.removeAuthStateListener(authStateListener)
        }
    }

    override fun firebaseSignIn(email: String, password: String):
            Flow<Response<Boolean>> = flow {
        operationSuccessFull = false
        try {
            emit(Response.Loading)
            auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
                operationSuccessFull = true
            }.await()
            delay(3000)
            emit(Response.Success(operationSuccessFull))

        } catch (e: Exception) {
            emit(Response.Error(e.localizedMessage ?: " unexpected error"))
        }
    }

    override fun forgetPassword(email: String): Flow<Response<Boolean>> = flow {
        operationSuccessFull = false
        try {
            emit(Response.Loading)
            auth.sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    operationSuccessFull = task.isSuccessful
                }.await()
            delay(3000)
            emit(Response.Success(operationSuccessFull))

        } catch (e: Exception) {
            emit(Response.Error(e.localizedMessage ?: " unexpected error"))
        }
    }

    override fun firebaseSignOut(): Flow<Response<Boolean>> = flow {
        try {
            emit(Response.Loading)

            auth.signOut()
            emit(Response.Success(true))

        } catch (e: Exception) {
            emit(Response.Error(e.localizedMessage ?: " unexpected error"))
        }
    }

    override fun firebaseSignUp(
        email: String,
        password: String,
        user: User
    ): Flow<Response<Boolean>> = flow {
        operationSuccessFull = false
        try {
            emit(Response.Loading)
            auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
                if (it.user != null) {
                    operationSuccessFull = true
                    val userId = auth.currentUser?.uid!!

                    fireStore.collection(Constants.FIREBASE_USER).document(userId).set(user)
                        .addOnSuccessListener {
                            Log.e(TAG, "firestore data uploaded: ", )
                        }
                }
            }.await()
            delay(3000)
           emit(Response.Success(operationSuccessFull))
        } catch (e: Exception) {
            emit(Response.Error(e.localizedMessage ?: " unexpected error"))
        }
    }
}