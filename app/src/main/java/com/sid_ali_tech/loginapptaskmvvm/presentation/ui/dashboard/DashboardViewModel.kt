package com.sid_ali_tech.loginapptaskmvvm.presentation.ui.dashboard

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sid_ali_tech.loginapptaskmvvm.data.model.User
import com.sid_ali_tech.loginapptaskmvvm.domain.usecase.DashboardUseCases
import com.sid_ali_tech.loginapptaskmvvm.utils.Constants.TAG
import com.sid_ali_tech.loginapptaskmvvm.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val dashboardUseCases: DashboardUseCases,
) : ViewModel() {

    private val _usersList =
        MutableStateFlow<Response<List<User>>>(Response.Success(emptyList()))
    val usersList: StateFlow<Response<List<User>>> = _usersList

    fun getUsersList() {
        viewModelScope.launch {
            dashboardUseCases.getUsersUseCase().collect() {
                _usersList.value = it
                Log.e(TAG, "ssss ${it.toString()}")
            }
        }
    }
}