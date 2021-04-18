package com.shubham.ishare.services

import android.util.Base64
import android.util.Log
import com.google.gson.Gson
import com.shubham.ishare.database.User
import java.lang.Exception

val gson = Gson()
class JWTUtils {
    fun decoded(jwtEncoded: String): User?{
        try {
            val split: List<String> = jwtEncoded.split(".")
             return gson.fromJson(getJson(split[1]), User::class.java)
        } catch(ex: Exception){
            Log.i("login", ex.message.toString())
        }
        return null
    }

    fun getJson(strEncoded: String): String{
        val decodedBytes: ByteArray? = Base64.decode(strEncoded, Base64.URL_SAFE)
        return String(decodedBytes!!, Charsets.UTF_8)
    }
}
