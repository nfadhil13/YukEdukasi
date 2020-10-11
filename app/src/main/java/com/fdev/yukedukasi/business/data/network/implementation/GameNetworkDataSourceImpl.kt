package com.fdev.yukedukasi.business.data.network.implementation

import com.fdev.yukedukasi.business.data.network.abstraction.GameNetworkDataSource
import com.fdev.yukedukasi.business.domain.model.Game
import com.fdev.yukedukasi.business.domain.model.Materi
import com.fdev.yukedukasi.framework.datasource.network.abstraction.GameNetworkService
import javax.inject.Inject

class GameNetworkDataSourceImpl
@Inject
constructor(
        private var gameNetworkService : GameNetworkService
) : GameNetworkDataSource
{
    override suspend fun getAllGamesCategories(): List<Game>
            = gameNetworkService.getAllGamesCategories()

    override suspend fun getGameMateri(gameId : Int): List<Materi>
            = gameNetworkService.getGameMateri(gameId)

}