package com.project.template.repo

import android.util.Log
import com.project.template.model.LoginRequestModel
import com.project.template.model.LoginResponseModel
import com.project.template.network.ApiWebServiceBackUp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginRdsBackUp(private var apiWebServiceBackUp: ApiWebServiceBackUp?) {

    private val TAG: String = LoginRdsBackUp::class.java.simpleName

    suspend fun loginRemoteApiCall(loginRequestModel: LoginRequestModel) {
        val loginCallBack = apiWebServiceBackUp?.loginApiCall(loginRequestModel)
        loginCallBack?.enqueue(object : Callback<LoginResponseModel> {
            override fun onResponse(call: Call<LoginResponseModel>, response: Response<LoginResponseModel>) {
                val token = response.body()
                Log.i("----->", "token$token")
            }

            override fun onFailure(call: Call<LoginResponseModel>, t: Throwable) {
                Log.i("----->", "error${t.message}")
            }
        })
    }
}