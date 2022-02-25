package com.project.template.repo

import com.project.template.model.LoginResponseModel
import kotlinx.coroutines.flow.Flow

class LoginRepoViaFlow(loginRDSViaFlow: LoginRDSViaFlow) {
    val loginResponseModel: Flow<LoginResponseModel?> = loginRDSViaFlow.loginApiCall
}