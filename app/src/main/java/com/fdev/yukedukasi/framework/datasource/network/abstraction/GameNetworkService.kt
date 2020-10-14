package com.fdev.yukedukasi.framework.datasource.network.abstraction

import com.fdev.yukedukasi.business.domain.model.Game
import com.fdev.yukedukasi.business.domain.model.Materi
import com.fdev.yukedukasi.business.domain.model.Soal

interface GameNetworkService{

    suspend fun getAllGamesCategories() : List<Game>
    suspend fun getGameMateri(id: Int): List<Materi>
    suspend fun getGameSoal(gameId: Int): List<Soal>
    suspend fun getSessionId(siswaId: Int, gamesId: Int): Int
    suspend fun updateSessionScore(sessionId: Int, score: Int)
    suspend fun updateAnswer(sessionId: Int, gameTestId: Int, answerId: Int)
}