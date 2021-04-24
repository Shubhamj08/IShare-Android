package com.shubham.ishare.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.shubham.ishare.services.JsonWebToken

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

    @Insert
    suspend fun insertToken(token: JsonWebToken)

    @Query("SELECT * FROM jwtTable")
    suspend fun getToken(): JsonWebToken?

    @Query("DELETE FROM jwtTable")
    suspend fun clearToken()
}