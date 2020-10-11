package com.fdev.yukedukasi.framework.datasource.network.model.materi


import com.google.gson.annotations.SerializedName

data class MateriDataResponse(
    @SerializedName("games_materi")
    val materiNetworkEntity: List<MateriNetworkEntity>
)