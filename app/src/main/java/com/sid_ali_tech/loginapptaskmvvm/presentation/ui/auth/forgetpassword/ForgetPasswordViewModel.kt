package com.sid_ali_tech.loginapptaskmvvm.presentation.ui.auth.forgetpassword

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.sid_ali_tech.loginapptaskmvvm.domain.usecase.AuthUseCases
import com.sid_ali_tech.loginapptaskmvvm.utils.Constants.TAG
import com.sid_ali_tech.loginapptaskmvvm.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgetPasswordViewModel @Inject constructor(private val authUseCases: AuthUseCases) : ViewModel() {


    private val _forgetPwdState = MutableStateFlow<Response<Boolean>>(Response.Success(false))
    val forgetPwdState: StateFlow<Response<Boolean>> = _forgetPwdState

    fun forgetPassword( email:String){
        viewModelScope.launch {
            authUseCases.forgetPassword(email = email).collect{
                _forgetPwdState.value=it
                Log.e(TAG, "in viewmodel  ${it.toString()}", )
            }
        }
    }

}