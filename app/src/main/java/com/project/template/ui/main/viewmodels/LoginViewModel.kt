package com.project.template.ui.main.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.template.model.LoginRequestModel
import com.project.template.model.LoginResponseModel
import com.project.template.model.LoginUiState
import com.project.template.model.LoginUiState.Error
import com.project.template.model.LoginUiState.Success
import com.project.template.repo.login.LoginRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private var loginRepo: LoginRepo) : ViewModel() {

    private val _uiState = MutableStateFlow<LoginUiState>(Success(LoginResponseModel("")))
    val uiState: StateFlow<LoginUiState> = _uiState

    fun loginApiViewModel(loginRequestModel: LoginRequestModel) {
        viewModelScope.launch {
            loginRepo.fetchLoginApiViaRepo(loginRequestModel)
                .catch { exception ->
                    _uiState.value = Error(exception)
                }
                .collect {
                    _uiState.value = Success(it)
                }
        }
    }

}