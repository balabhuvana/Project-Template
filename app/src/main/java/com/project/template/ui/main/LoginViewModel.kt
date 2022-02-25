package com.project.template.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.template.model.LoginRequestModel
import com.project.template.network.RetrofitClient
import com.project.template.repository.loginrepo.LoginRepository
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    fun loginApiViewModel(loginRequestModel: LoginRequestModel) {
        val loginRepository = LoginRepository(RetrofitClient.instance?.getMyApi())
        viewModelScope.launch {
            loginRepository.loginApiCallRepo(loginRequestModel)
        }
    }

}