package com.fdev.yukedukasi.framework.datasource.network.model.game_session


import com.google.gson.annotations.SerializedName

data class GameSessionNetworkResponse(
    @SerializedName("games_session")
    val gamesSession: GameSessionNetworkEntity
)