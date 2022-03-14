package com.project.template.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.project.template.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert
    suspend fun insertUser(user: User)

    @Delete
    suspend fun deleteUserByModel(user: User)

    @Query("DELETE FROM user_table WHERE id = :id")
    suspend fun deleteUserByID(id: Int)

    @Query("DELETE FROM user_table")
    suspend fun deleteAllUser()

    @Query("SELECT * FROM user_table WHERE id = :id")
    fun findUserById(id: Int): Flow<User>

    @Query("SELECT * FROM user_table WHERE first_name LIKE :firstName AND " + "last_name LIKE :lastName LIMIT 1")
    fun findUserByName(firstName: String, lastName: String): Flow<User>

    @Query("SELECT * FROM user_table")
    fun getAllUser(): Flow<List<User>>

}