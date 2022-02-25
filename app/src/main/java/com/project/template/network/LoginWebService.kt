package com.project.template.network

import com.project.template.model.LoginRequestModel
import com.project.template.model.LoginResponseModel
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginWebService {

    @POST("api/login")
    suspend fun loginApiCall(@Body loginRequestModel: LoginRequestModel): LoginResponseModel

    companion object {
        const val BASE_URL = "https://reqres.in/"
    }
}
