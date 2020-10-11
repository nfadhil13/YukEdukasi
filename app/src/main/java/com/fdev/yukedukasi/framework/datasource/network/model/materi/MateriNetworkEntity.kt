package com.fdev.yukedukasi.framework.datasource.network.model.materi


import com.google.gson.annotations.SerializedName

data class MateriNetworkEntity(
    @SerializedName("games_id")
    val gamesId: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("materi_games_id")
    val materiGamesId: String,
    @SerializedName("materi_name")
    val materiName: String,
    @SerializedName("seq")
    val seq: String,
    @SerializedName("sound")
    val sound: String
)