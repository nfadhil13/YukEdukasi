package com.fdev.yukedukasi.framework.datasource.network.apicall.services

import com.fdev.yukedukasi.framework.datasource.network.model.ApiResponse
import com.fdev.yukedukasi.framework.datasource.network.model.game.GameDataResponse
import retrofit2.http.GET

interface GameApiService{

    @GET("games/all")
    suspend fun getAll(): ApiResponse<GameDataResponse>
}