package com.project.template.model

import com.google.gson.annotations.SerializedName

data class User(

    @SerializedName("id")
    var userId: Int = 0,

    @SerializedName("first_name")
    var userFirstName: String = "",

    @SerializedName("last_name")
    var userLastName: String = "",

    @SerializedName("email")
    var userEmail: String? = null,

    @SerializedName("avatar")
    var userAvatar: String = ""
)