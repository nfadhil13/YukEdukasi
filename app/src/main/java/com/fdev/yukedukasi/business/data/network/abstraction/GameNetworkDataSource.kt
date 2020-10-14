package com.fdev.yukedukasi.business.data.network.abstraction

import com.fdev.yukedukasi.business.domain.model.Game
import com.fdev.yukedukasi.business.domain.model.Materi
import com.fdev.yukedukasi.business.domain.model.Soal

interface GameNetworkDataSource {

    suspend fun getAllGamesCategories() : List<Game>


    suspend fun getGameMateri(gameId : Int) : List<Materi>

    suspend fun getGameSoal(gameId : Int) : List<Soal>

    suspend fun getSessionId(siswaId : Int , gamesId : Int) : Int

    suspend fun updatSessionScore(sessionId : Int , score : Int)

    suspend fun updateAnswer(sessionId: Int , gameTestId : Int , answerId : Int)

}