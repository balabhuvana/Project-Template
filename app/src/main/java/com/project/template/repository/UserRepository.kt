package com.project.template.repository

import com.project.template.network.UserApiWebService

class UserRepository(private var userApiWebService: UserApiWebService?) {
    fun fetchNameListRequest() {
        val sampleRemoteDataSource = UserRemoteDataSource(userApiWebService)
        sampleRemoteDataSource.fetchSampleUser()
    }
}