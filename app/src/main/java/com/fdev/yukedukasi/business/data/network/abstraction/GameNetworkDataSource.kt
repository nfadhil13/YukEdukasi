package com.fdev.yukedukasi.business.data.network.abstraction

import com.fdev.yukedukasi.business.domain.model.Game
import com.fdev.yukedukasi.business.domain.model.Materi

interface GameNetworkDataSource {

    suspend fun getAllGamesCategories() : List<Game>


    suspend fun getGameMateri(gameId : Int) : List<Materi>

}