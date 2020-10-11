package com.fdev.yukedukasi.framework.presentation.main.materi.state

import android.os.Parcelable
import com.fdev.yukedukasi.business.domain.model.Game
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MateriViewState(
        var currentGame : Game? = null
) : Parcelable
