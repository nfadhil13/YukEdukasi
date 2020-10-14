package com.fdev.yukedukasi.framework.datasource.network.apicall.services

import com.fdev.yukedukasi.business.data.network.NetworkConstants
import com.fdev.yukedukasi.framework.datasource.network.model.ApiResponse
import com.fdev.yukedukasi.framework.datasource.network.model.game.GameDataResponse
import com.fdev.yukedukasi.framework.datasource.network.model.game_session.GameSessionNetworkResponse
import com.fdev.yukedukasi.framework.datasource.network.model.materi.MateriDataResponse
import com.fdev.yukedukasi.framework.datasource.network.model.siswa.SiswaDataResponse
import com.fdev.yukedukasi.framework.datasource.network.model.soal.SoalNetworkResponse
import retrofit2.http.*

interface GameApiService{

    @GET("games/all")
    suspend fun getAllGamesCategories(): ApiResponse<GameDataResponse>

    @GET("games_materi/all")
    suspend fun getGamesMateri(
            @Query("filter") gamesId : String,
            @Query("field") field : String = NetworkConstants.GAMES_ID_FILTER
    ) : ApiResponse<MateriDataResponse>


    @GET("games_test/all")
    suspend fun getGamesSoal(
            @Query("filter") gamesId : String,
            @Query("field") field : String = NetworkConstants.GAMES_TEST_FILTER
    ) : ApiResponse<SoalNetworkResponse>

    @POST("games_session/add")
    @FormUrlEncoded
    suspend fun getGameSession(
            @Field("siswa_id") siswaId : String,
            @Field("games_id") gameId : String
    ) : ApiResponse<GameSessionNetworkResponse>


    @POST("games_session/update")
    @FormUrlEncoded
    suspend fun updateGameScore(
            @Field("games_session_id") sessionId : String,
            @Field("score") score : Int
    ) : ApiResponse<Nothing>

    @POST("games_answer/add")
    @FormUrlEncoded
    suspend fun updateAnswer(
            @Field("games_session_id") sessionId : String,
            @Field("games_test_id") gameTest : String,
            @Field("user_games_materi_id") answerID : String
    )


}