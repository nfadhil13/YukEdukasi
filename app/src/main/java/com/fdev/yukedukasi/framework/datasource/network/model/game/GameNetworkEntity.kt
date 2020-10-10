package com.fdev.yukedukasi.framework.datasource.network.model.game


import com.google.gson.annotations.SerializedName

data class GameNetworkEntity(
    @SerializedName("games_id")
    val gamesId: String,
    @SerializedName("icon_games")
    val iconGames: String,
    @SerializedName("nama_games")
    val namaGames: String,
    @SerializedName("question_sound")
    val questionSound: String
)