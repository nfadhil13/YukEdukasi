package com.fdev.yukedukasi.framework.presentation

import com.fdev.yukedukasi.business.domain.state.Response
import com.fdev.yukedukasi.business.domain.state.StateMessageCallback
import com.fdev.yukedukasi.business.domain.state.UIComponentType

interface UIController {

    fun displayProgressBar(isDisplayed: Boolean)

    //fun hideSoftKeyboard()

    // fun displayInputCaptureDialog(title: String, callback: DialogInputCaptureCallback)

    //For ui interaction form the Fragment without Reponse (only message and the type)
    fun basicUIInteraction(message :String , uiComponentType: UIComponentType)

    fun onResponseReceived(
            response: Response,
            stateMessageCallback: StateMessageCallback
    )

}