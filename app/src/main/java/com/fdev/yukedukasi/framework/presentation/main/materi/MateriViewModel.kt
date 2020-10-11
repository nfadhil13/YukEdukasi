package com.fdev.yukedukasi.framework.presentation.main.materi

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import com.fdev.yukedukasi.business.domain.model.Game
import com.fdev.yukedukasi.business.domain.state.DataState
import com.fdev.yukedukasi.business.domain.state.StateEvent
import com.fdev.yukedukasi.business.interactors.main.materi.MateriInteractors
import com.fdev.yukedukasi.business.interactors.main.menu.MenuInteractors
import com.fdev.yukedukasi.framework.presentation.BaseViewModel
import com.fdev.yukedukasi.framework.presentation.main.materi.state.MateriStateEvent
import com.fdev.yukedukasi.framework.presentation.main.materi.state.MateriViewState
import com.fdev.yukedukasi.framework.presentation.main.menu.state.MenuStateEvent
import com.fdev.yukedukasi.framework.presentation.main.menu.state.MenuViewState
import com.fdev.yukedukasi.util.SessionManager
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow

@ExperimentalCoroutinesApi
@FlowPreview
class MateriViewModel
@ViewModelInject
constructor(
        val materiInteractors: MateriInteractors,
        val sessionManager: SessionManager,
        @Assisted private val savedStateHandle: SavedStateHandle
) : BaseViewModel<MateriViewState>() {



    override fun handleNewData(data: MateriViewState) {
        data.currentGame?.let{
            setMateriViewState(it)
        }
    }

    private fun setMateriViewState(game : Game) {
        val update = getCurrentViewStateOrNew()
        update.currentGame = game
        setViewState(update)
    }

    override fun setStateEvent(stateEvent: StateEvent) {
        val job: Flow<DataState<MateriViewState>?> = when(stateEvent){

            is MateriStateEvent.GetMateriOfGame -> {
                materiInteractors.getMateriOfGame.getMateriOfGames(stateEvent , stateEvent.game)
            }

            else -> {
                emitInvalidStateEvent(stateEvent)
            }
        }

        dataChannelManager.launchJob(stateEvent , job)
    }

    override fun initNewViewState(): MateriViewState = MateriViewState()


}