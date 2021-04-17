package com.shubham.ishare.services

import android.util.Base64
import android.util.Log
import java.lang.Exception

class JWTUtils {
    fun decoded(jwtEncoded: String){
        try {
            val split: List<String> = jwtEncoded.split(".")
            Log.i("login", getJson(split[1]))
        } catch(ex: Exception){
            Log.i("login", ex.message.toString())
        }
    }

    fun getJson(strEncoded: String): String{
        val decodedBytes: ByteArray? = Base64.decode(strEncoded, Base64.URL_SAFE)
        return String(decodedBytes!!, Charsets.UTF_8)
    }
}