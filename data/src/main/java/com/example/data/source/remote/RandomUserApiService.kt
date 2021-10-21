package com.example.data.source.remote

import com.example.data.models.ClientListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RandomUserApiService {
    @GET("api")
    suspend fun getRandomClients(@Query("results") count: Int) : ClientListResponse
}