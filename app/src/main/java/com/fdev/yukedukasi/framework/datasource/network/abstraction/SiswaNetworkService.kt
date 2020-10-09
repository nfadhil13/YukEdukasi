package com.fdev.yukedukasi.framework.datasource.network.abstraction

import com.fdev.yukedukasi.business.domain.model.Siswa
import com.fdev.yukedukasi.business.domain.model.User
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Header

interface SiswaNetworkService {

    suspend fun login(
            user : User
    ) : Siswa

}