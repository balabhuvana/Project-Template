package com.project.template.repo.login

import com.project.template.model.LoginRequestModel

class LoginRepoViaFlow(var loginRDSViaFlow: LoginRDSViaFlow) {

    fun fetchLoginApiViaRepo(loginRequestModel: LoginRequestModel) =
        loginRDSViaFlow.fetchLoginApi(loginRequestModel)

}
