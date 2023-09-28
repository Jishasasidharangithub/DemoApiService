package com.example.demoapiservice.data.Repository

import com.example.demoapiservice.data.remote.ApiService
import javax.inject.Inject

class Repository @Inject constructor(private val apiService: ApiService) {

    suspend fun fetchFaqs() = apiService.fetchFaqs()
}