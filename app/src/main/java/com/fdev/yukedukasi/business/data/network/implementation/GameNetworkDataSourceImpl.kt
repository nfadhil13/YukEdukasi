package com.fdev.yukedukasi.business.data.network.implementation

import com.fdev.yukedukasi.business.data.network.abstraction.GameNetworkDataSource
import com.fdev.yukedukasi.business.domain.model.Game
import com.fdev.yukedukasi.framework.datasource.network.abstraction.GameNetworkService
import javax.inject.Inject

class GameNetworkDataSourceImpl
@Inject
constructor(
        private var gameNetworkService : GameNetworkService
) : GameNetworkDataSource
{
    override suspend fun getAllGames(): List<Game>
            = gameNetworkService.getAllGames()

}