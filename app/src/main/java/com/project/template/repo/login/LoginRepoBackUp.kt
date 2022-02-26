package com.project.template.repo.login

import com.project.template.model.LoginRequestModel
import com.project.template.network.ApiWebServiceBackUp

class LoginRepoBackUp(private var apiWebServiceBackUp: ApiWebServiceBackUp?) {

    suspend fun loginApiCallRepo(loginRequestModel: LoginRequestModel) {
        val loginRDS = LoginRdsBackUp(apiWebServiceBackUp)
        loginRDS.loginRemoteApiCall(loginRequestModel)
    }

}