package com.fdev.yukedukasi.framework.datasource.network.mapper

import com.fdev.yukedukasi.business.domain.model.Siswa
import com.fdev.yukedukasi.framework.datasource.network.model.siswa.SiswaNetworkEntity
import com.fdev.yukedukasi.util.EntityMapper
import javax.inject.Inject

class SiswaNetworkMapper
@Inject
constructor()
    : EntityMapper<Siswa, SiswaNetworkEntity>(){
    override fun mapDomainToEntity(domain: SiswaNetworkEntity): Siswa {
        return Siswa(
                siswaId = domain.siswaId,
                nis = domain.nis,
                namaSiswa =  domain.namaSiswa,
                namaKelas = domain.namaKelas,
                pin = domain.pin

        )
    }

    override fun mapEntityToDomain(entity: Siswa): SiswaNetworkEntity {
        return SiswaNetworkEntity(
                siswaId = entity.siswaId,
                nis = entity.nis,
                namaSiswa =  entity.namaSiswa,
                namaKelas = entity.namaKelas,
                pin = entity.pin

        )
    }

}