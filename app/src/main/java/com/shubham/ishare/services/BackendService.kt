package com.shubham.ishare.services

import androidx.annotation.StringRes
import androidx.annotation.XmlRes
import com.shubham.ishare.auth.LoginCreds
import com.shubham.ishare.auth.RegisterCreds
import com.shubham.ishare.database.User
import com.shubham.ishare.ideas.Idea
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.create
import retrofit2.http.*
import java.lang.reflect.Type
import java.net.UnknownServiceException
import javax.xml.transform.OutputKeys.METHOD
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType


private const val BASE_URL = "https://ishare-server.herokuapp.com/api/"

data class JsonWebToken(
    val jsonWebToken: String
)

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()


interface BackendService {
    @GET("ideas")
    suspend fun getProperties(): List<Idea>

    @Headers("Content-Type: application/json")
    @POST("auth")
    suspend fun login(
        @Body user: LoginCreds
    ): JsonWebToken

    @Headers("Content-Type: application/json")
    @POST("users")
    suspend fun register(
        @Body user: RegisterCreds
    ): Object
}


object Backend {
    val retrofitService: BackendService by lazy {
        retrofit.create(BackendService::class.java)
    }
}