package com.fdev.yukedukasi.business.interactors.main.menu

import com.fdev.yukedukasi.business.data.network.NetworkResponseHandler
import com.fdev.yukedukasi.business.data.network.abstraction.GameNetworkDataSource
import com.fdev.yukedukasi.business.data.util.safeApiCall
import com.fdev.yukedukasi.business.domain.model.Game
import com.fdev.yukedukasi.business.domain.state.*
import com.fdev.yukedukasi.framework.presentation.main.menu.state.MenuViewState
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAllGame
@Inject
constructor(
       private var gameNetworkDataSource: GameNetworkDataSource
){

    companion object{
        const val  SUCCESS_RETRIVE_GAME = "Success get game datas"
    }

    fun getAllGame(
            stateEvent: StateEvent
    ) : Flow<DataState<MenuViewState>> = flow{

        val networkCall = safeApiCall(IO){
            gameNetworkDataSource.getAllGamesCategories()
        }

        val result = object : NetworkResponseHandler<MenuViewState, List<Game>>(
                stateEvent = stateEvent,
                response = networkCall
        ) {
            override suspend fun handleSuccess(resultObj: List<Game>): DataState<MenuViewState> {
                return DataState.data(
                        response = Response(
                                uiComponentType = UIComponentType.None(),
                                messageType = MessageType.Success(),
                                message = SUCCESS_RETRIVE_GAME
                        ),
                        data = MenuViewState(resultObj),
                        stateEvent = stateEvent
                )
            }

        }.getResult()

        emit(result)

    }

}