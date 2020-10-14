package com.fdev.yukedukasi.business.interactors.main.gamedetail

import com.fdev.yukedukasi.business.data.network.NetworkResponseHandler
import com.fdev.yukedukasi.business.data.network.abstraction.GameNetworkDataSource
import com.fdev.yukedukasi.business.data.util.safeApiCall
import com.fdev.yukedukasi.business.domain.model.Answer
import com.fdev.yukedukasi.business.domain.model.Game
import com.fdev.yukedukasi.business.domain.model.GameSession
import com.fdev.yukedukasi.business.domain.model.Soal
import com.fdev.yukedukasi.business.domain.state.*
import com.fdev.yukedukasi.framework.presentation.main.gamedetail.state.GameDetailViewState
import com.fdev.yukedukasi.framework.presentation.main.gamedetail.state.TestViewState
import com.fdev.yukedukasi.util.printLogD
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateTestScore
@Inject
constructor(
        private val gameNetworkDataSource: GameNetworkDataSource
) {

    companion object {

        const val SUBMIT_SCORE_SUCCESS = "Berhasil submit! , Nilai Anda : "

    }

    fun updateTestScore(
            stateEvent: StateEvent,
            gameSession: GameSession,
            answerList : List<Answer>
    ): Flow<DataState<GameDetailViewState>> = flow {


        val networkRequest = safeApiCall(Dispatchers.IO) {
            gameNetworkDataSource.updatSessionScore(gameSession.gameSessionId, gameSession.score)
            answerList.forEach {
                gameNetworkDataSource.updateAnswer(
                        sessionId = gameSession.gameSessionId ,
                           gameTestId =  it.gameTestId,
                        answerId =  it.choosenAnswer
                        )
            }
        }

        val result = object : NetworkResponseHandler<GameDetailViewState, Any>(
                stateEvent = stateEvent,
                response = networkRequest
        ) {


            override suspend fun handleSuccess(resultObj: Any): DataState<GameDetailViewState> {
                return DataState.data(
                        response = Response(
                                message = SUBMIT_SCORE_SUCCESS + gameSession.score.toString(),
                                messageType = MessageType.Success(),
                                uiComponentType = UIComponentType.Dialog()
                        ),
                        stateEvent = stateEvent,
                        data = null
                )
            }

        }.getResult()

        emit(result)
    }

}
