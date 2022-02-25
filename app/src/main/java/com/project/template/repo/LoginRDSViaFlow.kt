package com.project.template.repo

import com.project.template.model.LoginRequestModel
import com.project.template.network.LoginWebService
import kotlinx.coroutines.flow.flow

class LoginRDSViaFlow(private var loginWebService: LoginWebService?) {
    val loginApiCall = flow {
        val loginRequestModel = LoginRequestModel("eve.holt@reqres.in", "cityslicka")
        val loginResponseModel = loginWebService?.loginApiCall(loginRequestModel)
        emit(loginResponseModel)
    }
}