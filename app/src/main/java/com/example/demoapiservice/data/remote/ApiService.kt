package com.example.demoapiservice.data.remote

import com.example.demoapiservice.data.model.FaqsResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("faqs")
    suspend fun fetchFaqs(): Response<FaqsResponse>
}