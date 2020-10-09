package com.fdev.yukedukasi.framework.datasource.network.implementation

import com.fdev.yukedukasi.business.domain.model.Siswa
import com.fdev.yukedukasi.business.domain.model.User
import com.fdev.yukedukasi.framework.datasource.network.abstraction.SiswaNetworkService
import com.fdev.yukedukasi.framework.datasource.network.apicall.SiswaApiService
import com.fdev.yukedukasi.framework.datasource.network.mapper.SiswaNetworkMapper
import com.fdev.yukedukasi.util.ApiException
import com.fdev.yukedukasi.util.printLogD
import javax.inject.Inject

class SiswaNetworkServiceImpl
@Inject
constructor(
        private var siswaApiService: SiswaApiService,
        private var siswaNetworkMapper: SiswaNetworkMapper
) : SiswaNetworkService {


    override suspend fun login(user: User): Siswa {
        val result = siswaApiService.login(
                nis = user.nis,
                pin = user.pin
        )
        return result.data?.let{
            siswaNetworkMapper.mapDomainToEntity(
                    it.siswa
            )

        }?: throw ApiException(result.message)
    }


}