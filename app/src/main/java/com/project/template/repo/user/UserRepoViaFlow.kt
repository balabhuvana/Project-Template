package com.project.template.repo.user

import com.project.template.room.UserDao

class UserRepoViaFlow(var userRdsViaFlow: UserRdsViaFlow) {

    fun fetchUserListViaRepo() = userRdsViaFlow.fetchUserListRDSCall()

    suspend fun fetchUserListAndStoreItInRoomViaRepo() = userRdsViaFlow.fetchUserListAndStoreItInRoomViaRDS()

    fun fetchUserListRDSOfflineSupport() = userRdsViaFlow.fetchUserListRepoOfflineSupport()

    fun fetchUserDetailRepo(userId: String) = userRdsViaFlow.fetchUserDetailRdsCall(userId)

    fun fetchUserDetailFromRoomRepoCall(userId: Int) = userRdsViaFlow.fetchUserDetailFromRoomRdsCall(userId)

    suspend fun fetchUserDetailAndSaveItRoomRepoCall(userId: String) {
        userRdsViaFlow.fetchUserDetailAndSaveItRoomRdsCall(userId)
    }
}