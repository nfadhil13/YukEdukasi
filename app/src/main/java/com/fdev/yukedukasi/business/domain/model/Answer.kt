package com.fdev.yukedukasi.business.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Answer(
        val gameTestId : Int,
        val choosenAnswer : Int
) : Parcelable