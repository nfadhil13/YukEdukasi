package com.fdev.yukedukasi.framework.datasource.network.model.soal


import com.google.gson.annotations.SerializedName

data class SoalNetworkResponse(
    @SerializedName("games")
    val games: List<SoalNetworkEntity>
)