package com.fdev.yukedukasi.framework.datasource.network.apicall

import com.fdev.yukedukasi.framework.datasource.network.model.siswa.LoginResponse
import com.fdev.yukedukasi.framework.datasource.network.model.siswa.SiswaNetworkEntity
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*


interface SiswaApiService {

    @POST("siswa/login")
    @FormUrlEncoded
    suspend fun login(
            @Field("nis") nis : String,
            @Field("pin") pin : String
    ) : LoginResponse

}