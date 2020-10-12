package com.fdev.yukedukasi.framework.presentation.main.gamedetail.state

import android.os.Parcelable
import com.fdev.yukedukasi.business.domain.model.Game
import com.fdev.yukedukasi.business.domain.model.Materi
import com.fdev.yukedukasi.business.domain.model.Soal
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GameDetailViewState(
        var currentGame : Game? = null,

        var materiViewState: MateriViewState? = null,

        var testViewState: TestViewState? = null

) : Parcelable


@Parcelize
data class MateriViewState(
        var currentMateri: Materi? = null,
        var materiList : List<Materi>,
) : Parcelable

@Parcelize
data class TestViewState(
        var currentSoal: Soal? = null,
        var soalList : List<Soal>,
) : Parcelable

