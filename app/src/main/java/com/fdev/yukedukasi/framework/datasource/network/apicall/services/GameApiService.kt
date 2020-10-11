package com.fdev.yukedukasi.framework.datasource.network.apicall.services

import com.fdev.yukedukasi.business.data.network.NetworkConstants
import com.fdev.yukedukasi.framework.datasource.network.model.ApiResponse
import com.fdev.yukedukasi.framework.datasource.network.model.game.GameDataResponse
import com.fdev.yukedukasi.framework.datasource.network.model.materi.MateriDataResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GameApiService{

    @GET("games/all")
    suspend fun getAllGamesCategories(): ApiResponse<GameDataResponse>

    @GET("games_materi/all")
    suspend fun getGamesMateri(
            @Query("filter") gamesId : String,
            @Query("field") field : String = NetworkConstants.GAMES_ID_FILTER
    ) : ApiResponse<MateriDataResponse>
}