package com.fdev.yukedukasi.framework.datasource.network.implementation

import com.fdev.yukedukasi.business.domain.model.Game
import com.fdev.yukedukasi.business.domain.model.Materi
import com.fdev.yukedukasi.business.domain.model.Soal
import com.fdev.yukedukasi.framework.datasource.network.abstraction.GameNetworkService
import com.fdev.yukedukasi.framework.datasource.network.apicall.services.GameApiService
import com.fdev.yukedukasi.framework.datasource.network.mapper.GameNetworkMapper
import com.fdev.yukedukasi.framework.datasource.network.mapper.MateriNetworkMapper
import com.fdev.yukedukasi.framework.datasource.network.mapper.SoalNetworkMapper
import javax.inject.Inject

class GameNetworkServiceImpl
@Inject
constructor(
        private val gameApiService: GameApiService,
        private val gameNetworkMapper: GameNetworkMapper,
        private val materiNetworkMapper : MateriNetworkMapper,
        private val soalNetworkMapper: SoalNetworkMapper
) : GameNetworkService{

    override suspend fun getAllGamesCategories(): List<Game> {
        return gameNetworkMapper.mapDomainListToEntityList(
                gameApiService.getAllGamesCategories().data.games
        )
    }

    override suspend fun getGameMateri(id: Int): List<Materi> {
        return materiNetworkMapper.mapDomainListToEntityList(
                gameApiService.getGamesMateri(id.toString()).data.materiNetworkEntity
        )
    }

    override suspend fun getGameSoal(gameId: Int): List<Soal> {
        return soalNetworkMapper.mapDomainListToEntityList(
                gameApiService.getGamesSoal(gameId.toString()).data.games
        )
    }

    override suspend fun getSessionId(siswaId: Int, gamesId: Int): Int {
        return gameApiService.getGameSession(
                siswaId = siswaId.toString() ,
                gameId = gamesId.toString()
        ).data.gamesSession.gamesSessionId
    }

    override suspend fun updateSessionScore(sessionId: Int, score: Int){
        gameApiService.updateGameScore(sessionId.toString() , score)
    }

    override suspend fun updateAnswer(sessionId: Int, gameTestId: Int, answerId: Int) {
        gameApiService.updateAnswer(sessionId.toString() , gameTestId.toString() , answerId.toString())
    }

}