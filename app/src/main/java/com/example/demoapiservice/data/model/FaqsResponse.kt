package com.example.demoapiservice.data.model

import com.google.gson.annotations.SerializedName

data class FaqsResponse(

	@field:SerializedName("data")
	val data: List<FaqItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class FaqItem(

	@field:SerializedName("question")
	val question: String? = null,

	@field:SerializedName("answer")
	val answer: String? = null
)
