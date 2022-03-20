package com.project.template.repo.user

class UserRepoViaFlow(var userRdsViaFlow: UserRdsViaFlow) {

    fun fetchUserListViaRepo() = userRdsViaFlow.fetchUserListRDSCall()

    suspend fun fetchUserListAndStoreItInRoomViaRepo() = userRdsViaFlow.fetchUserListAndStoreItInRoomViaRDS()

    fun fetchUserListRDSOfflineSupport() = userRdsViaFlow.fetchUserListRepoOfflineSupport()

    fun fetchUserDetailRepo(userId: String) = userRdsViaFlow.fetchUserDetailRdsCall(userId)
}