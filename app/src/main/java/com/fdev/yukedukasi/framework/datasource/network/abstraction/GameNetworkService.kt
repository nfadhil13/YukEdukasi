package com.fdev.yukedukasi.framework.datasource.network.abstraction

import com.fdev.yukedukasi.business.domain.model.Game

interface GameNetworkService{

    suspend fun getAllGames() : List<Game>

}