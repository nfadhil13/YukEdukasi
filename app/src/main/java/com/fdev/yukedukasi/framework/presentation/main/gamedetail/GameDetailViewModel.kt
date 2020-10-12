package com.fdev.yukedukasi.framework.presentation.main.gamedetail

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import com.fdev.yukedukasi.business.domain.model.Game
import com.fdev.yukedukasi.business.domain.model.Materi
import com.fdev.yukedukasi.business.domain.state.DataState
import com.fdev.yukedukasi.business.domain.state.StateEvent
import com.fdev.yukedukasi.business.interactors.main.gamedetail.GameDetailInteractors
import com.fdev.yukedukasi.framework.presentation.BaseViewModel
import com.fdev.yukedukasi.framework.presentation.main.gamedetail.state.GameDetailStateEvent
import com.fdev.yukedukasi.framework.presentation.main.gamedetail.state.GameDetailViewState
import com.fdev.yukedukasi.framework.presentation.main.gamedetail.state.MateriViewState
import com.fdev.yukedukasi.framework.presentation.main.gamedetail.state.TestViewState
import com.fdev.yukedukasi.util.SessionManager
import com.fdev.yukedukasi.util.printLogD
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow

@ExperimentalCoroutinesApi
@FlowPreview
class GameDetailViewModel
@ViewModelInject
constructor(
        val materiInteractors: GameDetailInteractors,
        val sessionManager: SessionManager,
        @Assisted private val savedStateHandle: SavedStateHandle
) : BaseViewModel<GameDetailViewState>() {



    override fun handleNewData(data: GameDetailViewState) {
        data.let{viewState ->
            printLogD("handleNewData" , "$data")
            viewState.currentGame?.let{
                setGameViewState(it)
            }
            viewState.materiViewState?.let{
                setMateriViewState(it)
            }

            viewState.testViewState?.let{
                setTestViewState(it)
            }
        }
    }




    override fun setStateEvent(stateEvent: StateEvent) {
        val job: Flow<DataState<GameDetailViewState>?> = when(stateEvent){

            is GameDetailStateEvent.GetMateriOfGame -> {
                printLogD("setStateEvent" , " adalah ${stateEvent.game}")
                materiInteractors.getMateriOfGame.getMateriOfGames(stateEvent , stateEvent.game)
            }

            is GameDetailStateEvent.GetTestOfGame -> {
                materiInteractors.getTestOfGame.getTestOfMateri(stateEvent , stateEvent.game)
            }

            else -> {
                emitInvalidStateEvent(stateEvent)
            }
        }

        dataChannelManager.launchJob(stateEvent , job)
    }

    override fun initNewViewState(): GameDetailViewState = GameDetailViewState()


    fun getCurrentGame(): Game? {
        return getCurrentViewStateOrNew().currentGame
    }

    fun setCurrentGame(game : Game){
        setGameViewState(game)
    }

    private fun setGameViewState(game : Game) {
        val update = getCurrentViewStateOrNew()
        update.currentGame = game
        setViewState(update)
    }


    private fun setMateriViewState(materiViewState: MateriViewState) {
        val update = getCurrentViewStateOrNew()
        if(update.materiViewState?.currentMateri?.gameId != materiViewState.currentMateri?.gameId){
            update.materiViewState = materiViewState
            printLogD("setMateriViewState" , " adalah $materiViewState")
            setViewState(update)
        }
    }


    fun setCurrentMateri(materi : Materi){
        val update = getCurrentViewStateOrNew()
        if(materi.id != update.materiViewState?.currentMateri?.id){
            update.materiViewState?.currentMateri = materi
            setViewState(update)
        }
    }

    private fun setTestViewState(testViewState: TestViewState) {
        val update = getCurrentViewStateOrNew()

        if(update.testViewState?.currentSoal?.gameId!= testViewState.currentSoal?.gameId){
            update.testViewState = testViewState
            setViewState(update)
        }
    }

    fun reset() {
        setViewState(initNewViewState())
    }


}