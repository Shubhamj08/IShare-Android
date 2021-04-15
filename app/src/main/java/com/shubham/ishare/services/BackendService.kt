package com.shubham.ishare.services

import com.shubham.ishare.ideas.Idea
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET


private const val BASE_URL = "https://ishare-server.herokuapp.com/api/"

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
}

object Backend {
    val retrofitService: BackendService by lazy {
        retrofit.create(BackendService::class.java)
    }
}