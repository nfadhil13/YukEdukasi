package com.fdev.yukedukasi.business.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PilihanSoal(
        val materiId : Int,
        val image : String
) : Parcelable