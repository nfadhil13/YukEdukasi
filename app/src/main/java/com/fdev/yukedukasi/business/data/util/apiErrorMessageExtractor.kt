package com.fdev.yukedukasi.business.data.util

import com.fdev.yukedukasi.business.data.network.NetworkResult

const val SISWA_NOT_FOUND_ERROR = "Siswa not found"
const val SISWA_NOT_FOUND_ERROR_MESSAGE = "Siswa tidak terdaftar"

suspend fun apiErrorMessageExtractor(
        errorResponse : String
) : String{

    return when{

        errorResponse.contains(SISWA_NOT_FOUND_ERROR , true) -> SISWA_NOT_FOUND_ERROR_MESSAGE

        else -> errorResponse
    }

}