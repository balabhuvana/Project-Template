package com.project.template.repo

import com.project.template.model.LoginRequestModel

class LoginRepoViaFlow(var loginRDSViaFlow: LoginRDSViaFlow) {

    fun fetchLoginApiViaRepo(loginRequestModel: LoginRequestModel) =
        loginRDSViaFlow.fetchLoginApi(loginRequestModel)

}
