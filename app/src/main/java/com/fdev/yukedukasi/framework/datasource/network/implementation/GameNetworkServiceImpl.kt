package com.fdev.yukedukasi.framework.datasource.network.implementation

import com.fdev.yukedukasi.business.domain.model.Game
import com.fdev.yukedukasi.framework.datasource.network.abstraction.GameNetworkService
import com.fdev.yukedukasi.framework.datasource.network.apicall.services.GameApiService
import com.fdev.yukedukasi.framework.datasource.network.mapper.GameNetworkMapper
import javax.inject.Inject

class GameNetworkServiceImpl
@Inject
constructor(
        private val gameApiService: GameApiService,
        private val gameNetworkMapper: GameNetworkMapper
) : GameNetworkService{

    override suspend fun getAllGames(): List<Game> {
        return gameNetworkMapper.mapDomainListToEntityList(
                gameApiService.getAll().data.games
        )
    }

}