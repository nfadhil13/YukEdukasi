package com.fdev.yukedukasi.framework.datasource.network.model.game_session


import com.google.gson.annotations.SerializedName

data class GameSessionNetworkEntity(
    @SerializedName("games_session_id")
    val gamesSessionId: Int
)