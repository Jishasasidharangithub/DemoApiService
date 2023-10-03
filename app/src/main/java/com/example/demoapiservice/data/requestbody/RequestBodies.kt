package com.example.demoapiservice.data.requestbody

data class RegisterBody(
    val name: String,
    val email: String,
    val phone: String,
    val emirates: String,
    val company: String,
    val address: String,
    val area: String,
    val password: String,
    val passwordConfirmation: String,
    val branchId:Int,
    val deviceType: String,
    val deviceName: String,
    val deviceId: String,
    val token: String,
)
