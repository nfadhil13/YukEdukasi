package com.fdev.yukedukasi.framework.presentation.main.menu.state

import android.os.Parcelable
import com.fdev.yukedukasi.business.domain.model.Game
import com.fdev.yukedukasi.business.domain.model.Siswa
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MenuViewState(
        var gameList : List<Game>?  = null
) : Parcelable


