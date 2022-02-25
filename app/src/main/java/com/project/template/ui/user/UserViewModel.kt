package com.project.template.ui.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.template.network.RetrofitClient
import com.project.template.repository.userrepo.UserRepository
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {

    fun fetchNameListRequest() {
        val sampleRepository = UserRepository(RetrofitClient.instance?.getMyApi())
        viewModelScope.launch {
            sampleRepository.fetchNameListRequest()
        }

    }
}