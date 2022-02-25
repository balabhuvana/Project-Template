package com.project.template.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.template.model.LoginResponseModel
import com.project.template.model.LoginUiState
import com.project.template.repo.LoginRepoViaFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModelViaFlow : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState.Success(LoginResponseModel("")))
    val uiState: StateFlow<LoginUiState> = _uiState

    fun loginApiViewModel(loginRepoViaFlow: LoginRepoViaFlow) {
        viewModelScope.launch {
            loginRepoViaFlow.loginResponseModel.collect {
                _uiState.value = LoginUiState.Success(it)
            }
        }
    }

}