package com.example.demoapiservice.data.model

import com.google.gson.annotations.SerializedName

data class BranchResponse(

	@field:SerializedName("data")
	val data: List<BranchDataItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class BranchDataItem(

	@field:SerializedName("cities")
	val cities: List<String?>? = null,

	@field:SerializedName("branch_id")
	val branchId: Int? = null,

	@field:SerializedName("branch_name")
	val branchName: String? = null,

	@field:SerializedName("phone_digit_count")
	val phoneDigitCount: Int? = null,

	@field:SerializedName("phone_code")
	val phoneCode: Int? = null
)
