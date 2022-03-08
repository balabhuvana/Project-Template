package com.project.template.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.project.template.model.User

@Dao
interface UserDao {

    @Insert
    suspend fun insertUser(user: User)

    @Delete
    suspend fun removeUser(user: User)

    @Query("SELECT * FROM user_table WHERE first_name LIKE :firstName AND " + "last_name LIKE :lastName LIMIT 1")
    suspend fun findUserByName(firstName: String, lastName: String): User

    @Query("SELECT * FROM user_table")
    suspend fun getAllUser(): List<User>

}