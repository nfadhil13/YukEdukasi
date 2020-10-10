package com.fdev.yukedukasi.business.data.network.abstraction

import com.fdev.yukedukasi.business.domain.model.Siswa
import com.fdev.yukedukasi.business.domain.model.User

interface SiswaNetworkDataSource{

    suspend fun login(user : User) : Siswa


}