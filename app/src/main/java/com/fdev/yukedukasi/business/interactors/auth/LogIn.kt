package com.fdev.yukedukasi.business.interactors.auth

import com.fdev.yukedukasi.business.data.network.NetworkResponseHandler
import com.fdev.yukedukasi.business.data.network.abstraction.SiswaNetworkDataSource
import com.fdev.yukedukasi.business.data.util.safeApiCall
import com.fdev.yukedukasi.business.domain.model.Siswa
import com.fdev.yukedukasi.business.domain.model.User
import com.fdev.yukedukasi.business.domain.state.DataState
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LogIn
@Inject
constructor(
        private var networkDataSource: SiswaNetworkDataSource
){

    fun LogIn(user : User) : Flow<DataState<Siswa>?> = flow{

        val networkCall = safeApiCall(IO){
            networkDataSource.login(user)
        }

        val networkResponse = object : NetworkResponseHandler<Siswa , Siswa>(
                response = networkCall,
                stateEvent = null
        ){
            override suspend fun handleSuccess(resultObj: Siswa): DataState<Siswa>? {
                return DataState(
                        data = resultObj
                )
            }

        }.getResult()

        emit(networkResponse)
    }

}