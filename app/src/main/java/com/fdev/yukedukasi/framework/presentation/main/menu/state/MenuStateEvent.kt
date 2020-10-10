package com.fdev.yukedukasi.framework.presentation.main.menu.state

import com.fdev.yukedukasi.business.domain.state.StateEvent

sealed class MenuStateEvent : StateEvent {

    class GetAllGames() : MenuStateEvent() {
        override fun errorInfo(): String  = "Gagal mendapatkan data permainan"

        override fun eventName(): String  = "GetAllGames"

        override fun shouldDisplayProgressBar(): Boolean = true

    }

}