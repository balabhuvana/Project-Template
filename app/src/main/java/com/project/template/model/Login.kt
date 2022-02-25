package com.project.template.model

data class LoginRequestModel(val email: String, val password: String)

data class LoginResponseModel(val token: String?)

sealed class LoginUiState {
    data class Success(var loginResponseModel: LoginResponseModel?) : LoginUiState()
    data class Error(var exception: Throwable) : LoginUiState()
}