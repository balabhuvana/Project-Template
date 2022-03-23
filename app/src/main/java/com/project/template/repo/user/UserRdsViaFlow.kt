package com.project.template.repo.user

import android.util.Log
import com.project.template.network.ApiWebService
import com.project.template.room.UserDao
import kotlinx.coroutines.flow.flow

class UserRdsViaFlow(var userDao: UserDao, var apiWebService: ApiWebService?) {

    fun fetchUserListRDSCall() =
        flow {
            val userList = apiWebService?.fetchUserList()?.userModelList
            emit(userList)
        }

    suspend fun fetchUserListAndStoreItInRoomViaRDS() {
        userDao.insertUserList(apiWebService?.fetchUserList()?.userModelList)
    }

    fun fetchUserListRepoOfflineSupport() = userDao.getAllUser()

    fun fetchUserDetailRdsCall(userId: String) =
        flow {
            val user = apiWebService?.fetchUserDetail(userId)
            emit(user)
        }

    suspend fun fetchUserDetailAndSaveItRoomRdsCall(userId: String) {
        val singleUser = apiWebService?.fetchUserDetail(userId)
        userDao.insertSingleUser(singleUser)
    }

    fun fetchUserDetailFromRoomRdsCall(userId: Int) = userDao.findUserById(userId)
}
