package com.project.template.network

import com.project.template.model.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiWebService {

    @POST("api/login")
    suspend fun loginApiCall(@Body loginRequestModel: LoginRequestModel): LoginResponseModel

    @POST("api/register")
    suspend fun registrationApiCall(@Body registrationRequestModel: RegistrationRequestModel): RegistrationResponseModel

    @GET("api/users?page=2")
    suspend fun fetchUserList(): UserListRoot

    @GET("api/users/{id}")
    suspend fun fetchUserDetail(@Path("id") id: String): SingleUser

    companion object {
        const val BASE_URL = "https://reqres.in/"
    }
}
