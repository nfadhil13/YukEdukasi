package com.fdev.yukedukasi.framework.datasource.network.mapper

import com.fdev.yukedukasi.business.domain.model.Game
import com.fdev.yukedukasi.framework.datasource.network.model.game.GameNetworkEntity
import com.fdev.yukedukasi.util.EntityMapper
import javax.inject.Inject

class GameNetworkMapper
@Inject
constructor() : EntityMapper<Game,GameNetworkEntity>(){
    override fun mapDomainToEntity(domain: GameNetworkEntity): Game {
        return Game(
                id = domain.gamesId.toInt(),
                name =  domain.namaGames,
                image = domain.iconGames,
                sound = domain.questionSound
        )
    }

    override fun mapEntityToDomain(entity: Game): GameNetworkEntity {
        return GameNetworkEntity(
                gamesId = entity.id.toString(),
                iconGames = entity.image,
                namaGames = entity.name,
                questionSound = entity.sound
        )
    }

    fun mapDomainListToEntityList(domainList : List<GameNetworkEntity>) : List<Game>{
        val entityList = ArrayList<Game>()
        domainList.forEach { gameNetworkEntity ->
            entityList.add(mapDomainToEntity(gameNetworkEntity))
        }
        return entityList
    }

}