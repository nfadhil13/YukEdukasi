package com.fdev.yukedukasi.framework.presentation.main.gamedetail.state

import com.fdev.yukedukasi.business.domain.model.Game
import com.fdev.yukedukasi.business.domain.state.StateEvent

sealed class GameDetailStateEvent : StateEvent {

    class GetMateriOfGame(val game : Game) : GameDetailStateEvent() {
        override fun errorInfo(): String  = "Gagal mendapatkan materi game"

        override fun eventName(): String  = "GetMateriOfGame"

        override fun shouldDisplayProgressBar(): Boolean = true

    }

    class GetTestOfGame(val game : Game) : GameDetailStateEvent() {
        override fun errorInfo(): String  = "Gagal mendapatkan test game"

        override fun eventName(): String  = "GetTestOfGame"

        override fun shouldDisplayProgressBar(): Boolean = true

    }


}