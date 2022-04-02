package com.project.template.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "user_table")
data class User(

    @SerializedName("id")
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo("email")
    @SerializedName("email")
    var userEmail: String? = null,

    @ColumnInfo("first_name")
    @SerializedName("first_name")
    var userFirstName: String = "",

    @ColumnInfo("last_name")
    @SerializedName("last_name")
    var userLastName: String = "",

    @ColumnInfo("avatar")
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

data class SingleUser(@SerializedName("data") var user: User?) {
    companion object

}

sealed class UserUIState {
    data class Success(var userList: List<User>?) : UserUIState()
    data class Failure(var exception: Throwable) : UserUIState()
}

sealed class UserDetailUIState {
    data class Success(var user: User?) : UserDetailUIState()
    data class Failure(var exception: Throwable) : UserDetailUIState()
}