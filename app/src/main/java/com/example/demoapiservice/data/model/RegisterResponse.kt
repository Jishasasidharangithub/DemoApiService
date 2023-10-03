package com.example.demoapiservice.data.model

import android.service.autofill.UserData
import com.google.gson.annotations.SerializedName

data class RegisterResponse(

	@field:SerializedName("data")
	val data: UserData? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class UserData(

	@field:SerializedName("area")
	val area: String? = null,

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("branch_id")
	val branchId: String? = null,

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("branch_name")
	val branchName: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("currency")
	val currency: String? = null,

	@field:SerializedName("company")
	val company: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("emirates")
	val emirates: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("token")
	val token: String? = null
)
