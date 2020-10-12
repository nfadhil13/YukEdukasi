package com.fdev.yukedukasi.business.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Soal (
        val id : Int,
        val gameId : Int,
        val correctAnswerMaterSeq : Int,
        val soundPrefix : String,
        val sound : String,
        val pilihan : ArrayList<PilihanSoal> = ArrayList()
) : Parcelable