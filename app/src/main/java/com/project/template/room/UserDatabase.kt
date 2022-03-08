package com.project.template.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.project.template.model.User

@Database(entities = [User::class], version = 1)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}