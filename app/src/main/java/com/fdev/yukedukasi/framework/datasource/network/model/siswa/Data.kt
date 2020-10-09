package com.fdev.yukedukasi.framework.datasource.network.model.siswa


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("siswa")
    val siswa: SiswaNetworkEntity
)