package com.fdev.yukedukasi.framework.datasource.network.model.siswa


import com.google.gson.annotations.SerializedName

data class SiswaDataResponse(
    @SerializedName("siswa")
    val siswa: SiswaNetworkEntity
)