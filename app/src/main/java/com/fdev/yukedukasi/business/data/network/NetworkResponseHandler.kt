package com.fdev.yukedukasi.business.data.network

import com.fdev.yukedukasi.business.data.network.NetworkErrors.NETWORK_DATA_NULL
import com.fdev.yukedukasi.business.data.network.NetworkErrors.NETWORK_ERROR
import com.fdev.yukedukasi.business.domain.state.*

abstract class NetworkResponseHandler <ViewState, Data>(
        private val response: NetworkResult<Data?>,
        private val stateEvent: StateEvent?
){

    suspend fun getResult(): DataState<ViewState>? {

        return when(response){

            is NetworkResult.GenericError -> {
                DataState.error(
                        response = Response(
                                message = "${stateEvent?.errorInfo()}\n\nReason: ${response.errorMessage.toString()}",
                                uiComponentType = UIComponentType.Dialog(),
                                messageType = MessageType.Error()
                        ),
                        stateEvent = stateEvent
                )
            }

            is NetworkResult.NetworkError -> {
                DataState.error(
                        response = Response(
                                message = "${stateEvent?.errorInfo()}\n\nReason: ${NETWORK_ERROR}",
                                uiComponentType = UIComponentType.Dialog(),
                                messageType = MessageType.Error()
                        ),
                        stateEvent = stateEvent
                )
            }

            is NetworkResult.Success -> {
                if(response.value == null){
                    DataState.error(
                            response = Response(
                                    message = "${stateEvent?.errorInfo()}\n\nReason: ${NETWORK_DATA_NULL}.",
                                    uiComponentType = UIComponentType.Dialog(),
                                    messageType = MessageType.Error()
                            ),
                            stateEvent = stateEvent
                    )
                }
                else{
                    handleSuccess(resultObj = response.value)
                }
            }

        }
    }

    abstract suspend fun handleSuccess(resultObj: Data): DataState<ViewState>?

}