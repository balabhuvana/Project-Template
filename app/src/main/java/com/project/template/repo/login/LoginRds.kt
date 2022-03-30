package com.project.template.repo.login

import com.project.template.model.LoginRequestModel
import com.project.template.network.ApiWebService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class LoginRds(private var apiWebService: ApiWebService?) {

    fun fetchLoginApi(loginRequestModel: LoginRequestModel) = flow {
        val loginResponseModel = apiWebService?.loginApiCall(loginRequestModel)
        emit(loginResponseModel)
    }.flowOn(Dispatchers.IO)

}
