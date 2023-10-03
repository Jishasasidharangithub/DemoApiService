package com.example.demoapiservice.data.Repository

import com.example.demoapiservice.data.remote.ApiService
import com.example.demoapiservice.data.requestbody.RegisterBody
import retrofit2.Response
import retrofit2.http.GET
import javax.inject.Inject

class Repository @Inject constructor(private val apiService: ApiService) {

    suspend fun fetchFaqs() = apiService.fetchFaqs()
    suspend fun fetchBranchList() = apiService.fetchBranchList()
    suspend fun register(
        body: RegisterBody
    ) = apiService.register(
        body.name,
        body.email,
        body.phone,
        body.emirates,
        body.company,
        body.address,
        body.area,
        body.password,
        body.passwordConfirmation,
        body.deviceType,
        body.deviceName,
        body.deviceId,
        body.token,
        body. branchId,
    )


}