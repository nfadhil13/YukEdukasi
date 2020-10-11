package com.fdev.yukedukasi.framework.presentation.main.materi.state

import com.fdev.yukedukasi.business.domain.model.Game
import com.fdev.yukedukasi.business.domain.state.StateEvent

sealed class MateriStateEvent : StateEvent {

    class GetMateriOfGame(val game : Game) : MateriStateEvent() {
        override fun errorInfo(): String  = "Gagal mendapatkan materi game"

        override fun eventName(): String  = "GetMateriOfGame"

        override fun shouldDisplayProgressBar(): Boolean = true

    }

}