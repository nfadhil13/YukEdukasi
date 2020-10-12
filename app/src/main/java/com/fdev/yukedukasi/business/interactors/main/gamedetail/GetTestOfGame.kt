package com.fdev.yukedukasi.business.interactors.main.gamedetail

import com.fdev.yukedukasi.business.data.network.NetworkResponseHandler
import com.fdev.yukedukasi.business.data.network.abstraction.GameNetworkDataSource
import com.fdev.yukedukasi.business.data.util.safeApiCall
import com.fdev.yukedukasi.business.domain.model.Game
import com.fdev.yukedukasi.business.domain.model.Soal
import com.fdev.yukedukasi.business.domain.state.*
import com.fdev.yukedukasi.framework.presentation.main.gamedetail.state.GameDetailViewState
import com.fdev.yukedukasi.framework.presentation.main.gamedetail.state.MateriViewState
import com.fdev.yukedukasi.framework.presentation.main.gamedetail.state.TestViewState
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetTestOfGame
@Inject
constructor(
        private val gameNetworkDataSource: GameNetworkDataSource
) {

    companion object {

        const val SUCCES_GET_TEST_GAME = "Berhasil mendapatkan data test game"
        const val FAILED_GET_TEST_GAME = "Gagal mendapatkan data test game"

    }

    fun getTestOfMateri(
            stateEvent: StateEvent,
            game: Game
    ): Flow<DataState<GameDetailViewState>> = flow {

        val networkRequest = safeApiCall(IO) {
            //Create empty list
            val soalList = ArrayList<Soal>()

            //Check if game materi is empty
            if (game.gameMateri.isEmpty()) {

                //Get game materi from network
                val materiList = gameNetworkDataSource.getGameMateri(game.id)

                //If not null insert to game
                if(!materiList.isEmpty()){
                    game.insertMateri(materiList)
                    soalList.addAll(game.generateSoalList(gameNetworkDataSource.getGameSoal(game.id)))
                }
            }else{
                soalList.addAll(game.generateSoalList(gameNetworkDataSource.getGameSoal(game.id)))
            }
            soalList
        }

        val result = object : NetworkResponseHandler<GameDetailViewState, List<Soal>>(
                stateEvent = stateEvent,
                response = networkRequest
        ) {
            override suspend fun handleSuccess(resultObj: List<Soal>): DataState<GameDetailViewState> {
                if (!resultObj.isEmpty()) {
                    game.clearMateri()
                    game.insertPertanyaan(resultObj)
                    return DataState.data(
                            response = Response(
                                    message = SUCCES_GET_TEST_GAME,
                                    messageType = MessageType.Success(),
                                    uiComponentType = UIComponentType.None()
                            ),
                            stateEvent = stateEvent,
                            data = GameDetailViewState(currentGame = game, testViewState = TestViewState(
                                    resultObj[0] , resultObj)
                            ))
                }else{
                    return DataState.error(
                            response = Response(
                                    message = FAILED_GET_TEST_GAME,
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
