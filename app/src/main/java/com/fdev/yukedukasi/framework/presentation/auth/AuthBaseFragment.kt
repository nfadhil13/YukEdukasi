package com.fdev.yukedukasi.framework.presentation.auth

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.fdev.yukedukasi.business.domain.state.StateMessage
import com.fdev.yukedukasi.business.domain.state.StateMessageCallback
import com.fdev.yukedukasi.framework.presentation.UIController
import com.fdev.yukedukasi.util.printLogD
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
abstract class AuthBaseFragment : Fragment(){

    lateinit var uiController: UIController

    lateinit var stateMessageCallback: StateMessageCallback

    val viewModel : AuthViewModel by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setupChannel()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        setUIController(context)
    }

    private fun setUIController(activityContext : Context) {
        activity?.let{
            if(it is AuthActivity){
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
        subcribeObserver()
    }

    private fun initStateMessageCallback() {
        stateMessageCallback= object : StateMessageCallback{
            override fun removeMessageFromStack() {
                viewModel.clearAllStateMessages()
            }

        }
    }

    private fun subcribeObserver() {
        viewModel.shouldDisplayProgressBar.observe(viewLifecycleOwner , Observer {
            printLogD("AuthBaseFragment" , "boolean  : $it" )
            uiController.displayProgressBar(it)
        })

        viewModel.stateMessage.observe(viewLifecycleOwner , {
            it?.let{   stateMassage ->
                handleStateMessage(
                        stateMassage,
                        stateMessageCallback
                )
            }
        })
    }

    open fun handleStateMessage(stateMessage: StateMessage, stateMessageCallback: StateMessageCallback){
        uiController.onResponseReceived(
                stateMessage.response,
                stateMessageCallback
        )
    }

}