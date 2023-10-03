package com.example.demoapiservice.data.remote

import com.example.demoapiservice.data.model.BranchResponse
import com.example.demoapiservice.data.model.FaqsResponse
import com.example.demoapiservice.data.model.RegisterResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("phone") phone: String,
        @Field("emirates") emirates: String,
        @Field("company") company: String,
        @Field("address") address: String,
        @Field("area") area: String,
        @Field("password") password: String,
        @Field("password_confirmation") passwordConfirmation: String,
        @Field("device_type") deviceType: String,
        @Field("device_name") deviceName: String,
        @Field("device_id") deviceId: String,
        @Field("token") token: String,
        @Field("branch_id") branchId: Int
    ): Response<RegisterResponse>
    @GET("branches")
    suspend fun fetchBranchList(): Response<BranchResponse>
    @GET("faqs")
    suspend fun fetchFaqs(): Response<FaqsResponse>
}