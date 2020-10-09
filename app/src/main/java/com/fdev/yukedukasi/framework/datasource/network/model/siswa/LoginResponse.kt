package com.fdev.yukedukasi.framework.datasource.network.model.siswa


import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("data")
    val data: Data?,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)