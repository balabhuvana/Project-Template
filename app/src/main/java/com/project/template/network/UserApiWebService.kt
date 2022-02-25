package com.project.template.network

import com.project.template.model.LoginRequestModel
import com.project.template.model.LoginResponseModel
import com.project.template.model.UserListRoot
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserApiWebService {

    @GET("api/users?page=2")
    fun getUserList(): Call<UserListRoot>

    @POST("api/login")
    suspend fun loginApiCall(@Body loginRequestModel: LoginRequestModel): Call<LoginResponseModel>

    companion object {
        const val BASE_URL = "https://reqres.in/"
    }
}
