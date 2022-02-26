package com.project.template.network

import com.project.template.model.LoginRequestModel
import com.project.template.model.LoginResponseModel
import com.project.template.model.RegistrationRequestModel
import com.project.template.model.RegistrationResponseModel
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiWebService {

    @POST("api/login")
    suspend fun loginApiCall(@Body loginRequestModel: LoginRequestModel): LoginResponseModel

    @POST("api/register")
    suspend fun registrationApiCall(@Body registrationRequestModel: RegistrationRequestModel): RegistrationResponseModel

    companion object {
        const val BASE_URL = "https://reqres.in/"
    }
}
