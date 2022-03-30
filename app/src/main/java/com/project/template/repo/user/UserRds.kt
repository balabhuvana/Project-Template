package com.project.template.repo.user

import com.project.template.network.ApiWebService
import com.project.template.room.UserDao
import kotlinx.coroutines.flow.flow

class UserRds(var userDao: UserDao, var apiWebService: ApiWebService?) {

    fun fetchUserListRDSCall() =
        flow {
            val userList = apiWebService?.fetchUserList()?.userModelList
            emit(userList)
        }

    suspend fun fetchUserListFromRestAndStoreItInRoomViaRDS() {
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
        userDao.insertUser(singleUser?.user)
    }

    fun fetchUserDetailFromRoomRdsCall(userId: Int) = userDao.findUserById(userId)
}
