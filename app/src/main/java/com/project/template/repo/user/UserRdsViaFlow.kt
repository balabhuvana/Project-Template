package com.project.template.repo.user

import com.project.template.network.ApiWebService
import kotlinx.coroutines.flow.flow

class UserRdsViaFlow(var apiWebService: ApiWebService?) {

    fun fetchUserListRDSCall() =
        flow {
            emit(apiWebService?.fetchUserList())
        }

    fun fetchUserDetailRdsCall(userId: String) =
        flow {
            val user = apiWebService?.fetchUserDetail(userId)
            emit(user)
        }
}
