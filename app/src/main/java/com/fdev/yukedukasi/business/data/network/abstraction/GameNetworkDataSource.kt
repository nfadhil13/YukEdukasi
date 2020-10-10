package com.fdev.yukedukasi.business.data.network.abstraction

import com.fdev.yukedukasi.business.domain.model.Game

interface GameNetworkDataSource {

    suspend fun getAllGames() : List<Game>

}