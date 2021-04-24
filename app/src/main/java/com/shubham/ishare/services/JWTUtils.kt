package com.shubham.ishare.services

import android.util.Base64
import android.util.Log
import com.google.gson.Gson
import com.shubham.ishare.database.User
import com.shubham.ishare.jwt
import java.lang.Exception

val gson = Gson()
class JWTUtils {

    //Decode JWT received from backend
    fun decoded(jwtEncoded: String): User?{
        try {
            val split: List<String> = jwtEncoded.split(".")
             return gson.fromJson(getJson(split[1]), User::class.java)
        } catch(ex: Exception){
            Log.i("login", ex.message.toString())
        }
        return null
    }

    //convert JWT to JSON User object
    fun getJson(strEncoded: String): String{
        val decodedBytes: ByteArray? = Base64.decode(strEncoded, Base64.URL_SAFE)
        return String(decodedBytes!!, Charsets.UTF_8)
    }
}
