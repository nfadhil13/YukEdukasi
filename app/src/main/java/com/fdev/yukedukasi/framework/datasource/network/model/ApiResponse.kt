package com.fdev.yukedukasi.framework.datasource.network.model

import com.google.gson.annotations.SerializedName

class ApiResponse<Data>(
        @SerializedName("data")
        val data: Data?,
        @SerializedName("message")
        val message: String,
        @SerializedName("status")
        val status: Boolean
)