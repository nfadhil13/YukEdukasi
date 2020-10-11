package com.fdev.yukedukasi.framework.datasource.network.mapper

import com.fdev.yukedukasi.business.data.network.NetworkConstants
import com.fdev.yukedukasi.business.domain.model.Materi
import com.fdev.yukedukasi.framework.datasource.network.model.materi.MateriNetworkEntity
import com.fdev.yukedukasi.util.EntityMapper
import javax.inject.Inject

class MateriNetworkMapper
@Inject
constructor():  EntityMapper<Materi, MateriNetworkEntity>() {

    override fun mapDomainToEntity(domain: MateriNetworkEntity): Materi {
        return Materi(
                id = domain.materiGamesId.toInt(),
                name =  domain.materiName,
                image =  NetworkConstants.FILE_BASE_URL + domain.image,
                sound = NetworkConstants.FILE_BASE_URL + domain.sound,
                seq = domain.seq.toInt(),
                gameId = domain.gamesId.toInt()
        )
    }

    override fun mapEntityToDomain(entity: Materi): MateriNetworkEntity {
        return MateriNetworkEntity(
                gamesId = entity.gameId.toString(),
                materiName =  entity.name,
                materiGamesId = entity.id.toString(),
                image = entity.image,
                sound = entity.sound,
                seq = entity.seq.toString()
        )
    }

    fun mapDomainListToEntityList(list : List<MateriNetworkEntity>) : List<Materi> {
        val entityList = ArrayList<Materi>()
        list.forEach {
            entityList.add(mapDomainToEntity(it))
        }
        return entityList
    }

}