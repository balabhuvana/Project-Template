package com.project.template.repo

import com.project.template.model.LoginRequestModel
import com.project.template.network.LoginWebService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class LoginRDSViaFlow(private var loginWebService: LoginWebService?) {

    fun fetchLoginApi(loginRequestModel: LoginRequestModel) = flow {
        val loginResponseModel = loginWebService?.loginApiCall(loginRequestModel)
        emit(loginResponseModel)
    }.flowOn(Dispatchers.IO)

}
