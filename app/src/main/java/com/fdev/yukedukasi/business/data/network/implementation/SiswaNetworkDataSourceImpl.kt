package com.fdev.yukedukasi.business.data.network.implementation

import com.fdev.yukedukasi.business.data.network.abstraction.SiswaNetworkDataSource
import com.fdev.yukedukasi.business.domain.model.Siswa
import com.fdev.yukedukasi.business.domain.model.User
import com.fdev.yukedukasi.framework.datasource.network.abstraction.SiswaNetworkService
import com.fdev.yukedukasi.framework.datasource.network.apicall.SiswaApiService
import javax.inject.Inject

class SiswaNetworkDataSourceImpl
@Inject
constructor(
      private val siswaNetworkService: SiswaNetworkService
) : SiswaNetworkDataSource {

    override suspend fun login(user: User): Siswa
        = siswaNetworkService.login(user)

}