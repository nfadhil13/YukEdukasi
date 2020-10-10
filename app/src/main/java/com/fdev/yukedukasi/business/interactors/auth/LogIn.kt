package com.fdev.yukedukasi.business.interactors.auth

import com.fdev.yukedukasi.business.data.network.NetworkResponseHandler
import com.fdev.yukedukasi.business.data.network.abstraction.SiswaNetworkDataSource
import com.fdev.yukedukasi.business.data.util.safeApiCall
import com.fdev.yukedukasi.business.domain.model.Siswa
import com.fdev.yukedukasi.business.domain.model.User
import com.fdev.yukedukasi.business.domain.state.*
import com.fdev.yukedukasi.framework.presentation.auth.state.AuthViewState
import com.fdev.yukedukasi.framework.presentation.auth.state.LoginViewState
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LogIn
@Inject
constructor(
        private var networkDataSource: SiswaNetworkDataSource
){

    companion object{
        const val  LOGIN_SUCCESS = "Log in Success"
    }

    fun LogIn(
            user : User,
            stateEvent : StateEvent
    ) : Flow<DataState<AuthViewState>> = flow{

        val networkCall = safeApiCall(IO){
            networkDataSource.login(user)
        }

        val networkResponse = object : NetworkResponseHandler<AuthViewState , Siswa>(
                response = networkCall,
                stateEvent = stateEvent
        ){
            override suspend fun handleSuccess(resultObj: Siswa): DataState<AuthViewState> {
                return DataState.data(
                        response = Response(
                                message = LOGIN_SUCCESS,
                                uiComponentType =  UIComponentType.None(),
                                messageType = MessageType.Success()
                        ),
                        data = AuthViewState(
                                loginViewState = LoginViewState(siswa = resultObj)
                        ),
                        stateEvent = stateEvent
                )
            }

        }.getResult()

        emit(networkResponse)
    }

}