package com.project.template.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitClient private constructor() {
    private val apiWebService: ApiWebService

    init {

        val retrofit: Retrofit =
            Retrofit.Builder()
                .baseUrl(ApiWebService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        apiWebService = retrofit.create(ApiWebService::class.java)
    }

    fun getMyApi(): ApiWebService {
        return apiWebService
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