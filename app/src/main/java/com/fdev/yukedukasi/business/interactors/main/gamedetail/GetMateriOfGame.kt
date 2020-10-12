package com.fdev.yukedukasi.business.interactors.main.gamedetail

import com.fdev.yukedukasi.business.data.network.NetworkResponseHandler
import com.fdev.yukedukasi.business.data.network.abstraction.GameNetworkDataSource
import com.fdev.yukedukasi.business.data.util.safeApiCall
import com.fdev.yukedukasi.business.domain.model.Game
import com.fdev.yukedukasi.business.domain.model.Materi
import com.fdev.yukedukasi.business.domain.state.*
import com.fdev.yukedukasi.framework.presentation.main.gamedetail.state.GameDetailViewState
import com.fdev.yukedukasi.framework.presentation.main.gamedetail.state.MateriViewState
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMateriOfGame
@Inject
constructor(
        private val gameNetworkDataSource: GameNetworkDataSource
) {

    companion object{

        const val SUCCES_GET_MATERI_GAME = "Berhasil mendapatkan data materi game"
        const val FAILED_GET_MATERI_GAME = "Gagal mendapatkan data materi game"

    }

    fun getMateriOfGames(
        stateEvent : StateEvent,
        game : Game
    ) : Flow<DataState<GameDetailViewState>> = flow{

        val networkRequest = safeApiCall(IO){
            gameNetworkDataSource.getGameMateri(game.id)
        }

        val result = object : NetworkResponseHandler<GameDetailViewState,List<Materi>>(
                stateEvent = stateEvent,
                response = networkRequest
        ){
            override suspend fun handleSuccess(resultObj: List<Materi>): DataState<GameDetailViewState> {
                if(!resultObj.isEmpty()){
                    game.clearMateri()
                    game.insertMateri(resultObj)
                    return DataState.data(
                            response = Response(
                                    message = SUCCES_GET_MATERI_GAME ,
                                    messageType = MessageType.Success(),
                                    uiComponentType = UIComponentType.None()
                            ),
                            stateEvent = stateEvent,
                            data = GameDetailViewState(currentGame = game  , MateriViewState(
                                    materiList = resultObj,
                                    currentMateri = resultObj[0]
                            ))
                    )
                }else{
                    return DataState.error(
                            response = Response(
                                    message = FAILED_GET_MATERI_GAME,
                                    messageType = MessageType.Error(),
                                    uiComponentType = UIComponentType.Dialog()
                            ),
                            stateEvent = stateEvent)

                }

            }

        }.getResult()

        emit(result)
    }

}
