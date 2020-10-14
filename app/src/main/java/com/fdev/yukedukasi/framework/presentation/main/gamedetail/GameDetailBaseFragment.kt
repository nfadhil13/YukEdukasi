package com.fdev.yukedukasi.framework.presentation.main.gamedetail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.fdev.yukedukasi.R
import com.fdev.yukedukasi.business.domain.state.StateMessageCallback
import com.fdev.yukedukasi.business.domain.state.UIComponentType
import com.fdev.yukedukasi.framework.presentation.main.MainBaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview


@FlowPreview
@ExperimentalCoroutinesApi
abstract class GameDetailBaseFragment : MainBaseFragment() {

    // Should use navGraphViewModel , but there is bug
    val viewModel: GameDetailViewModel by activityViewModels()


    private var _requestManager: RequestManager? = null

    val requestManager
        get() = _requestManager!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setupChannel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserver()
    }

    private fun initObserver() {
            viewModel.shouldDisplayProgressBar.observe(viewLifecycleOwner, {
            uiController.displayProgressBar(it)
        })

        viewModel.stateMessage.observe(viewLifecycleOwner , {
            it?.let{
                handleStateMessage(it , stateMessageCallback)
            }

        })
    }

    fun showErrorMessage() {
        uiController.basicUIInteraction(
                uiComponentType = UIComponentType.Dialog(),
                message = getString(R.string.change_fragment_fail_message)
        )
        findNavController().navigate(R.id.menuFragment)
    }

    fun initGlide() {
        _requestManager = Glide.with(requireActivity())
    }

    override fun initStateMessageCallback() {
        stateMessageCallback = object : StateMessageCallback {
            override fun removeMessageFromStack() {
                viewModel.clearAllStateMessages()
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _requestManager = null
    }


}