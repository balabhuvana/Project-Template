package com.project.template.repository.loginrepo

import com.project.template.model.LoginRequestModel
import com.project.template.network.UserApiWebService

class LoginRepository(private var userApiWebService: UserApiWebService?) {

    suspend fun loginApiCallRepo(loginRequestModel: LoginRequestModel){
        val loginRemoteDataSource = LoginRemoteDataSource(userApiWebService)
        loginRemoteDataSource.loginRemoteApiCall(loginRequestModel)
    }

}