package com.project.template.repository

import android.util.Log
import com.project.template.model.UserListRoot
import com.project.template.network.UserApiWebService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRemoteDataSource(private var userApiWebService: UserApiWebService?) {

    fun fetchSampleUser() {
        val callBackUserList = userApiWebService?.getUserList()
        callBackUserList?.enqueue(object : Callback<UserListRoot> {
            override fun onResponse(
                call: Call<UserListRoot>,
                response: Response<UserListRoot>
            ) {
                Log.i("-----> ", "Success")
            }

            override fun onFailure(call: Call<UserListRoot>, t: Throwable) {
                Log.i("-----> ", "Failure - " + t.message)
            }
        })
    }

}