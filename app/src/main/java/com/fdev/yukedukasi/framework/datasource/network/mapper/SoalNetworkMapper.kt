package com.fdev.yukedukasi.framework.datasource.network.mapper

import com.fdev.yukedukasi.business.domain.model.Game
import com.fdev.yukedukasi.business.domain.model.Soal
import com.fdev.yukedukasi.framework.datasource.network.model.game.GameNetworkEntity
import com.fdev.yukedukasi.framework.datasource.network.model.soal.SoalNetworkEntity
import com.fdev.yukedukasi.util.EntityMapper
import javax.inject.Inject

class SoalNetworkMapper
@Inject
constructor() : EntityMapper<Soal, SoalNetworkEntity>(){

    override fun mapDomainToEntity(domain: SoalNetworkEntity): Soal {
        return Soal(
                gameId = domain.gamesId.toInt(),
                id = domain.gamesTestId.toInt(),
                correctAnswerMaterSeq = domain.seqGamesTestId.toInt(),
                soundPrefix = domain.questionSound,
                sound = domain.sound
        )
    }

    override fun mapEntityToDomain(entity: Soal): SoalNetworkEntity {
        return SoalNetworkEntity(
                gamesId = entity.gameId.toString(),
                gamesTestId = entity.id.toString(),
                questionSound = entity.soundPrefix,
                seqGamesTestId = entity.correctAnswerMaterSeq.toString(),
                sound = entity.sound

        )
    }

    fun mapDomainListToEntityList(domainList : List<SoalNetworkEntity>) : List<Soal>{
        val entityList = ArrayList<Soal>()
        domainList.forEach { soalNetworkEntity ->
            entityList.add(mapDomainToEntity(soalNetworkEntity))
        }
        return entityList
    }
}