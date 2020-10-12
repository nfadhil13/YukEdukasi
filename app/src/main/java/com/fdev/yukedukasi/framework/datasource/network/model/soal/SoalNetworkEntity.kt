package com.fdev.yukedukasi.framework.datasource.network.model.soal


import com.google.gson.annotations.SerializedName

data class SoalNetworkEntity(
    @SerializedName("correct_games_materi_id")
    val correctGamesMateriId: String = "",
    @SerializedName("games_id")
    val gamesId: String,
    @SerializedName("games_test_id")
    val gamesTestId: String,
    @SerializedName("question_sound")
    val questionSound: String,
    @SerializedName("seq_games_test_id")
    val seqGamesTestId: String,
    @SerializedName("sound")
    val sound: String
)