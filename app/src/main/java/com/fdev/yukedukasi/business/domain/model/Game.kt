package com.fdev.yukedukasi.business.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Game(
        val id : Int,
        val gameName : String,
        val gameIcon : String,
        val gameSound : String
) : Parcelable