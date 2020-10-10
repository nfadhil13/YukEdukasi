package com.fdev.yukedukasi.framework.datasource.network.apicall

import com.fdev.yukedukasi.framework.datasource.network.model.ApiResponse
import com.fdev.yukedukasi.framework.datasource.network.model.siswa.SiswaDataResponse
import retrofit2.http.*


interface SiswaApiService {

    @POST("siswa/login")
    @FormUrlEncoded
    suspend fun login(
            @Field("nis") nis : String,
            @Field("pin") pin : String
    ) : ApiResponse<SiswaDataResponse>

}