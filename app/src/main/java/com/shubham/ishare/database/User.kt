package com.shubham.ishare.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.shubham.ishare.services.gson
import java.lang.reflect.Type

@Entity(tableName = "LoginTable")
data class User(
    @ColumnInfo(name = "username")
    val username: String,
    @ColumnInfo(name = "email")
    val email: String,
    @PrimaryKey
    val _id: String,
    @ColumnInfo(name = "ideas")
    val ideas: List<String>,
    @ColumnInfo(name = "iat")
    val iat: Long
){
    override fun toString(): String {
        return "{username: ${username}, email: ${email}, _id: ${_id}, ideas: $ideas}"
    }
}

public class Converters {
    @TypeConverter
    fun fromString(value: String): List<String>{
        val listType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<String>): String{
        return gson.toJson(list)
    }
}