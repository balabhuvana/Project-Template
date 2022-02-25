package com.project.template.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitClient private constructor() {
    private val loginWebService: LoginWebService

    init {

        val retrofit: Retrofit =
            Retrofit.Builder()
                .baseUrl(LoginWebService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        loginWebService = retrofit.create(LoginWebService::class.java)
    }

    fun getMyApi(): LoginWebService {
        return loginWebService
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