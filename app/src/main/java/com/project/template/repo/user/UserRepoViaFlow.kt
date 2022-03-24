package com.project.template.repo.user

class UserRepoViaFlow(var userRdsViaFlow: UserRdsViaFlow) {

    fun fetchUserListViaRepo() = userRdsViaFlow.fetchUserListRDSCall()

    suspend fun fetchUserListFromRestAndStoreItInRoomViaRepo() = userRdsViaFlow.fetchUserListFromRestAndStoreItInRoomViaRDS()

    fun fetchUserListRDSOfflineSupport() = userRdsViaFlow.fetchUserListRepoOfflineSupport()

    fun fetchUserDetailRepo(userId: String) = userRdsViaFlow.fetchUserDetailRdsCall(userId)

    fun fetchUserDetailFromRoomRepoCall(userId: Int) = userRdsViaFlow.fetchUserDetailFromRoomRdsCall(userId)

    suspend fun fetchUserDetailAndSaveItRoomRepoCall(userId: String) {
        userRdsViaFlow.fetchUserDetailAndSaveItRoomRdsCall(userId)
    }
}