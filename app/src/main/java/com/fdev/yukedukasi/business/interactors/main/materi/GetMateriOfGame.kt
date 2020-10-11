package com.fdev.yukedukasi.business.interactors.main.materi

import com.fdev.yukedukasi.business.data.network.NetworkResponseHandler
import com.fdev.yukedukasi.business.data.network.abstraction.GameNetworkDataSource
import com.fdev.yukedukasi.business.data.util.safeApiCall
import com.fdev.yukedukasi.business.domain.model.Game
import com.fdev.yukedukasi.business.domain.model.Materi
import com.fdev.yukedukasi.business.domain.state.*
import com.fdev.yukedukasi.framework.presentation.main.materi.state.MateriViewState
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

        const val SUCCES_GET_DATA_GAME = "Berhasil mendapatkan data materi game"

    }

    fun getMateriOfGames(
        stateEvent : StateEvent,
        game : Game
    ) : Flow<DataState<MateriViewState>> = flow{

        val networkRequest = safeApiCall(IO){
            gameNetworkDataSource.getGameMateri(game.id)
        }

        val result = object : NetworkResponseHandler<MateriViewState,List<Materi>>(
                stateEvent = stateEvent,
                response = networkRequest
        ){
            override suspend fun handleSuccess(resultObj: List<Materi>): DataState<MateriViewState> {
                if(!resultObj.isEmpty()){
                    game.insertMateri(resultObj)
                }
                return DataState.data(
                        response = Response(
                                message = SUCCES_GET_DATA_GAME ,
                                messageType = MessageType.Success(),
                                uiComponentType = UIComponentType.None()
                        ),
                        stateEvent = stateEvent,
                        data = MateriViewState(game)
                )
            }

        }.getResult()

        emit(result)
    }

}
