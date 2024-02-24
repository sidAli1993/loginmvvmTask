package com.sid_ali_tech.loginapptaskmvvm.hilt

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.sid_ali_tech.loginapptaskmvvm.common.CustomProgressBar
import com.sid_ali_tech.loginapptaskmvvm.data.repository.AuthRepositoryImpl
import com.sid_ali_tech.loginapptaskmvvm.data.repository.GetUsersRepositoryImpl
import com.sid_ali_tech.loginapptaskmvvm.domain.repository.AuthRepository
import com.sid_ali_tech.loginapptaskmvvm.domain.repository.GetUsersRepository
import com.sid_ali_tech.loginapptaskmvvm.domain.usecase.AuthUseCases
import com.sid_ali_tech.loginapptaskmvvm.domain.usecase.DashboardUseCases
import com.sid_ali_tech.loginapptaskmvvm.domain.usecase.authusecases.AuthState
import com.sid_ali_tech.loginapptaskmvvm.domain.usecase.authusecases.ForgetPassword
import com.sid_ali_tech.loginapptaskmvvm.domain.usecase.authusecases.IsUserAuthenticated
import com.sid_ali_tech.loginapptaskmvvm.domain.usecase.authusecases.SignIn
import com.sid_ali_tech.loginapptaskmvvm.domain.usecase.authusecases.SignOut
import com.sid_ali_tech.loginapptaskmvvm.domain.usecase.authusecases.SignUp
import com.sid_ali_tech.loginapptaskmvvm.domain.usecase.dashboardusecases.GetUsersUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HiltModule {

    @Provides
    @Singleton
    fun providesFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun providesDatabaseReference(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun providesFirebaseUser(): FirebaseStorage {
        return FirebaseStorage.getInstance()
    }


    @Provides
    @Singleton
    fun providesAuthRepository(auth: FirebaseAuth, firestore: FirebaseFirestore): AuthRepository {
        return AuthRepositoryImpl(auth, firestore)
    }

    @Provides
    fun providesAuthUseCases(authRepository: AuthRepository): AuthUseCases {
        return AuthUseCases(
            authState = AuthState(authRepository),
            isUserAuthenticated = IsUserAuthenticated(authRepository),
            signIn = SignIn(authRepository),
            signOut = SignOut(authRepository),
            signUp = SignUp(authRepository),
            forgetPassword = ForgetPassword(authRepository)
        )
    }

    @Provides
    fun providesCustomProgressBar(): CustomProgressBar {
        return CustomProgressBar()
    }

    @Provides
    fun providesCategoryRepository(firestore: FirebaseFirestore,firebaseAuth: FirebaseAuth): GetUsersRepository {
        return GetUsersRepositoryImpl(firestore,firebaseAuth)
    }

    @Provides
    fun providesDashboardUsecases(getUsersRepository: GetUsersRepository): DashboardUseCases {
        return DashboardUseCases(
            getUsersUseCase = GetUsersUseCase(getUsersRepository),
        )
    }
}