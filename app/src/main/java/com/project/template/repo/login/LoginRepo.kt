package com.project.template.repo.login

import com.project.template.model.LoginRequestModel

class LoginRepo(var loginRds: LoginRds) {

    fun fetchLoginApiViaRepo(loginRequestModel: LoginRequestModel) =
        loginRds.fetchLoginApi(loginRequestModel)

}
