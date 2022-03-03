package com.project.template.model

import com.google.gson.annotations.SerializedName

data class User(

    @SerializedName("id")
    var userId: Int = 0,

    @SerializedName("email")
    var userEmail: String? = null,

    @SerializedName("first_name")
    var userFirstName: String = "",

    @SerializedName("last_name")
    var userLastName: String = "",

    @SerializedName("avatar")
    var userAvatar: String = ""
)


data class UserListRoot(
    @SerializedName("page")
    var page: Int = 2,

    @SerializedName("per_page")
    var perPage: Int = 0,

    @SerializedName("total")
    var total: Int = 0,

    @SerializedName("total_pages")
    var totaPages: Int = 0,

    @SerializedName("data")
    var userModelList: List<User>? = null
)

sealed class UserUIState() {
    data class Success(var userListRoot: UserListRoot?) : UserUIState()
    data class Failure(var exception: Throwable) : UserUIState()
}