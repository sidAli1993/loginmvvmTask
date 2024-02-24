package com.sid_ali_tech.loginapptaskmvvm.presentation.ui.auth.signup

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.sid_ali_tech.loginapptaskmvvm.data.model.User
import com.sid_ali_tech.loginapptaskmvvm.domain.usecase.AuthUseCases
import com.sid_ali_tech.loginapptaskmvvm.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class SignupViewModel
@Inject constructor
    (private val authUseCases: AuthUseCases):ViewModel() {
    val password=MutableLiveData<String>("")

    val isUserAuthenticated get() = authUseCases.isUserAuthenticated()

    private val _signUpState= MutableStateFlow<Response<Boolean>>(Response.Success(false))
    val signUpState : StateFlow<Response<Boolean>> = _signUpState

    private val _signOutState=MutableStateFlow<Response<Boolean>>(Response.Success(false))
    val signOutState:StateFlow<Response<Boolean>> = _signOutState

    private val _authState=MutableStateFlow<Boolean>(false)
    val authState:StateFlow<Boolean> = _authState

    private val _signInState = MutableStateFlow<Response<Boolean>>(Response.Success(false))
    val signInState: StateFlow<Response<Boolean>> = _signInState

    fun signUp(user: User){
        viewModelScope.launch {
            authUseCases.signUp(email =user.email, password = password.value.toString(), user = user).collect{
                _signUpState.value=it
            }
        }
    }

    fun getAuthState(){
        viewModelScope.launch {
            authUseCases.authState().collect(){
                _authState.value=it
            }
        }
    }

}