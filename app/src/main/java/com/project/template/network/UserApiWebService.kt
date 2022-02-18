package com.project.template.network

import com.project.template.model.UserListRoot
import retrofit2.Call
import retrofit2.http.GET

interface UserApiWebService {

    @GET("api/users?page=2")
    fun getUserList(): Call<UserListRoot>

    companion object {
        const val BASE_URL = "https://reqres.in/"
    }
}
