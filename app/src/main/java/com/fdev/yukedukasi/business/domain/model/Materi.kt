package com.fdev.yukedukasi.business.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Materi(
        val gameId : Int,
        val id : Int,
        val name : String,
        val image : String,
        val sound : String,
        val seq : Int
) : Parcelable