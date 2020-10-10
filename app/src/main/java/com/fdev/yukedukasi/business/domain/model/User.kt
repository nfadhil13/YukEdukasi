package com.fdev.yukedukasi.business.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
        val nis : String,
        val pin : String
) : Parcelable