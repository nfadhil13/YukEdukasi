package com.fdev.yukedukasi.framework.presentation.main

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.fdev.yukedukasi.business.domain.state.StateMessage
import com.fdev.yukedukasi.business.domain.state.StateMessageCallback
import com.fdev.yukedukasi.framework.presentation.UIController
import com.fdev.yukedukasi.framework.presentation.auth.AuthActivity
import com.fdev.yukedukasi.framework.presentation.auth.AuthViewModel

abstract class MainBaseFragment  : Fragment(){


    lateinit var uiController: UIController

    lateinit var stateMessageCallback: StateMessageCallback





    override fun onAttach(context: Context) {
        super.onAttach(context)
        setUIController(context)
    }

    private fun setUIController(activityContext : Context) {
        activity?.let{
            if(it is MainActivity){
                try{
                    uiController = activityContext as UIController
                }catch (e : ClassCastException){
                    e.printStackTrace()
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initStateMessageCallback()
    }

    abstract fun initStateMessageCallback()

    open fun handleStateMessage(stateMessage: StateMessage, stateMessageCallback: StateMessageCallback){
        uiController.onResponseReceived(
                stateMessage.response,
                stateMessageCallback
        )
    }


}