package com.project.template.repo.user

class UserRepo(var userRds: UserRds) {

    fun fetchUserListViaRepo() = userRds.fetchUserListRDSCall()

    suspend fun fetchUserListFromRestAndStoreItInRoomViaRepo() = userRds.fetchUserListFromRestAndStoreItInRoomViaRDS()

    fun fetchUserListRDSOfflineSupport() = userRds.fetchUserListRepoOfflineSupport()

    fun fetchUserDetailRepo(userId: String) = userRds.fetchUserDetailRdsCall(userId)

    fun fetchUserDetailFromRoomRepoCall(userId: Int) = userRds.fetchUserDetailFromRoomRdsCall(userId)

    suspend fun fetchUserDetailAndSaveItRoomRepoCall(userId: String) {
        userRds.fetchUserDetailAndSaveItRoomRdsCall(userId)
    }
}