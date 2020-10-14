package com.fdev.yukedukasi.business.domain.model

import android.os.Parcelable
import dagger.multibindings.IntoMap
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GameSession (
        var gameSessionId : Int,
        var score : Int,
        var siswaId : Int,
        var gamesId : Int
) : Parcelable