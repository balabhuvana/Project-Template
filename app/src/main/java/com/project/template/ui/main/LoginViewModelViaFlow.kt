package com.project.template.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.template.model.LoginRequestModel
import com.project.template.model.LoginResponseModel
import com.project.template.model.LoginUiState
import com.project.template.model.LoginUiState.Error
import com.project.template.model.LoginUiState.Success
import com.project.template.repo.LoginRepoViaFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class LoginViewModelViaFlow : ViewModel() {

    private val _uiState = MutableStateFlow<LoginUiState>(Success(LoginResponseModel("")))
    val uiState: StateFlow<LoginUiState> = _uiState

    fun loginApiViewModel(loginRequestModel: LoginRequestModel, loginRepoViaFlow: LoginRepoViaFlow) {
        viewModelScope.launch {
            loginRepoViaFlow.fetchLoginApiViaRepo(loginRequestModel)
                .catch { exception ->
                    _uiState.value = Error(exception)
                }
                .collect {
                    _uiState.value = Success(it)
                }
        }
    }

}