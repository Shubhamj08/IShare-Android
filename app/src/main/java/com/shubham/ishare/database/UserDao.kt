package com.shubham.ishare.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {
    @Insert
    suspend fun insert(user: User)

    @Update
    suspend fun update(user: User)

    @Query("SELECT * FROM LoginTable")
    suspend fun get(): User?

    @Query("DELETE FROM LoginTable")
    suspend fun clear()
}