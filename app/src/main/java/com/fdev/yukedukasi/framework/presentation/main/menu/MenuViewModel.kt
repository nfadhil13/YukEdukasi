package com.fdev.yukedukasi.framework.presentation.main.menu

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import com.fdev.yukedukasi.business.domain.model.Game
import com.fdev.yukedukasi.business.domain.state.DataState
import com.fdev.yukedukasi.business.domain.state.StateEvent
import com.fdev.yukedukasi.business.interactors.main.menu.MenuInteractors
import com.fdev.yukedukasi.framework.presentation.BaseViewModel
import com.fdev.yukedukasi.framework.presentation.main.menu.state.MenuStateEvent
import com.fdev.yukedukasi.framework.presentation.main.menu.state.MenuViewState
import com.fdev.yukedukasi.util.SessionManager
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow

@ExperimentalCoroutinesApi
@FlowPreview
class MenuViewModel
@ViewModelInject
constructor(
        val menuInteractors: MenuInteractors,
        val sessionManager: SessionManager,
        @Assisted private val savedStateHandle: SavedStateHandle
) : BaseViewModel<MenuViewState>() {


    override fun handleNewData(data: MenuViewState) {
        data.gameList?.let{
            setMenuViewState(it)
        }
    }



    override fun setStateEvent(stateEvent: StateEvent) {
        val job: Flow<DataState<MenuViewState>?> = when(stateEvent){

            is MenuStateEvent.GetAllGames -> {
                menuInteractors.getAllGame.getAllGame(stateEvent = stateEvent)
            }

            else -> {
                emitInvalidStateEvent(stateEvent)
            }
        }

        dataChannelManager.launchJob(stateEvent , job)
    }


    private fun setMenuViewState(gamelist : List<Game>){
        val update = getCurrentViewStateOrNew()
        update.gameList = gamelist
        setViewState(update)
    }

    override fun initNewViewState(): MenuViewState {
        return MenuViewState()
    }
}