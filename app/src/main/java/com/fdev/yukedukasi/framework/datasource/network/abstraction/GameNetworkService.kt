package com.fdev.yukedukasi.framework.datasource.network.abstraction

import com.fdev.yukedukasi.business.domain.model.Game
import com.fdev.yukedukasi.business.domain.model.Materi

interface GameNetworkService{

    suspend fun getAllGamesCategories() : List<Game>
    suspend fun getGameMateri(id: Int): List<Materi>

}