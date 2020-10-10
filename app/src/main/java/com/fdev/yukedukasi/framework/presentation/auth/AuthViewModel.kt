package com.fdev.yukedukasi.framework.presentation.auth

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import com.fdev.yukedukasi.business.domain.model.Siswa
import com.fdev.yukedukasi.business.domain.model.User
import com.fdev.yukedukasi.business.domain.state.DataState
import com.fdev.yukedukasi.business.domain.state.StateEvent
import com.fdev.yukedukasi.business.interactors.auth.AuthInteractors
import com.fdev.yukedukasi.framework.presentation.BaseViewModel
import com.fdev.yukedukasi.framework.presentation.auth.state.AuthStateEvent
import com.fdev.yukedukasi.framework.presentation.auth.state.AuthViewState
import com.fdev.yukedukasi.framework.presentation.auth.state.LoginViewState
import com.fdev.yukedukasi.framework.presentation.auth.state.SplashViewState
import com.fdev.yukedukasi.util.SessionManager
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow

@ExperimentalCoroutinesApi
@FlowPreview
class AuthViewModel
@ViewModelInject
constructor(
        val authInteractors: AuthInteractors,
        val sessionManager: SessionManager,
        @Assisted private val savedStateHandle: SavedStateHandle
) : BaseViewModel<AuthViewState>() {


    override fun handleNewData(data: AuthViewState) {
        data.let { viewState ->
            viewState.splashViewState?.let { splashViewState ->
                setSplashViewState(splashViewState)
            }
            viewState.loginViewState?.let { loginViewState ->
                setLoginViewState(loginViewState)

            }
        }
    }


    override fun setStateEvent(stateEvent: StateEvent) {
        val job: Flow<DataState<AuthViewState>?> = when (stateEvent) {

            is AuthStateEvent.LoginStateEvent -> {
                authInteractors.loginInCase.LogIn(
                        user = stateEvent.user,
                        stateEvent = stateEvent
                )
            }

            is AuthStateEvent.SyncStateEvent -> {
                authInteractors.sycnSiswaCase.syncSiswa(
                        user = stateEvent.user,
                        stateEvent = stateEvent
                )
            }

            else -> {
                emitInvalidStateEvent(stateEvent)
            }
        }

        launchJob(stateEvent, job)
    }


    fun checkLastUserLogIn(): User? {
        return sessionManager.checkLastUserLogIn()
    }

    fun logIn(siswa: Siswa) {
        sessionManager.login(siswa)
    }

    private fun setSplashViewState(splashViewState: SplashViewState) {
        val update = getCurrentViewStateOrNew()
        update.splashViewState = splashViewState
        setViewState(update)
    }


    private fun setLoginViewState(loginViewState: LoginViewState) {
        val update = getCurrentViewStateOrNew()
        update.loginViewState = loginViewState
        setViewState(update)
    }

    override fun initNewViewState(): AuthViewState = AuthViewState()

}