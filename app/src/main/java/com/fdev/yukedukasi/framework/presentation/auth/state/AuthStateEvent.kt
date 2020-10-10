package com.fdev.yukedukasi.framework.presentation.auth.state

import com.fdev.yukedukasi.business.domain.model.User
import com.fdev.yukedukasi.business.domain.state.StateEvent

sealed class AuthStateEvent : StateEvent {

    class LoginStateEvent(
            var user : User,
    ) : AuthStateEvent(){
        override fun errorInfo(): String {
            return "Error Logging in"
        }

        override fun eventName(): String {
            return "LoginStateEvent"
        }

        override fun shouldDisplayProgressBar(): Boolean {
            return true
        }

    }

    class SyncStateEvent(
            var user : User,
    ) : AuthStateEvent(){
        override fun errorInfo(): String {
            return "Error Logging in"
        }

        override fun eventName(): String {
            return "LoginStateEvent"
        }

        override fun shouldDisplayProgressBar(): Boolean {
            return false
        }

    }

}