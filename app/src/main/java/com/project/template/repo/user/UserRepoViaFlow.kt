package com.project.template.repo.user

class UserRepoViaFlow(var userRdsViaFlow: UserRdsViaFlow) {

    fun fetchUserListViaRepo() = userRdsViaFlow.fetchUserListRDSCall()

}