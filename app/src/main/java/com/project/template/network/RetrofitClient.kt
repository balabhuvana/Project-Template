package com.project.template.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitClient private constructor() {
    private val userApiWebService: UserApiWebService

    init {

        val retrofit: Retrofit =
            Retrofit.Builder()
                .baseUrl(UserApiWebService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        userApiWebService = retrofit.create(UserApiWebService::class.java)
    }

    fun getMyApi(): UserApiWebService {
        return userApiWebService
    }

    companion object {
        @get:Synchronized
        var instance: RetrofitClient? = null
            get() {
                if (field == null) {
                    field = RetrofitClient()
                }
                return field
            }
            private set
    }
}