package com.shubham.ishare.services

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.shubham.ishare.auth.LoginCreds
import com.shubham.ishare.auth.RegisterCreds
import com.shubham.ishare.ideas.Idea
import com.shubham.ishare.ideas.post.Post
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType


private const val BASE_URL = "https://ishare-server.herokuapp.com/api/"

@Entity(tableName = "jwtTable")
data class JsonWebToken(
    @PrimaryKey
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

    @PUT("ideas/like")
    suspend fun like(
        @Header("x-auth-token") jwt: String, @Body idea: Idea
    ): Idea

    @PUT("ideas/dislike")
    suspend fun unlike(
        @Header("x-auth-token") jwt: String, @Body idea: Idea
    ): Idea

    @Headers("Content-Type: application/json")
    @POST("ideas")
    suspend fun postIdea(
        @Header("x-auth-token") jwt: String, @Body post: Post
    )
}


object Backend {
    val retrofitService: BackendService by lazy {
        retrofit.create(BackendService::class.java)
    }
}