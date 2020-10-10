package com.fdev.yukedukasi.framework.datasource.network.model.game



import com.google.gson.annotations.SerializedName

data class GameDataResponse(
    @SerializedName("games")
    val games: List<GameNetworkEntity>
)