package com.fdev.yukedukasi.business.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Siswa(
        val siswaId : String,
        val nis : String,
        val namaSiswa : String,
        val namaKelas : String,
        val pin : String
) :Parcelable