package com.fdev.yukedukasi.framework.presentation.main.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.fdev.yukedukasi.business.domain.state.StateMessageCallback
import com.fdev.yukedukasi.databinding.FragmentMenuBinding
import com.fdev.yukedukasi.framework.presentation.main.MainBaseFragment
import com.fdev.yukedukasi.framework.presentation.main.menu.state.MenuStateEvent
import com.fdev.yukedukasi.util.printLogD
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview


@AndroidEntryPoint
@FlowPreview
@ExperimentalCoroutinesApi
class MenuFragment : MainBaseFragment() {

    private var _binding: FragmentMenuBinding? = null

    private val binding
        get() = _binding!!


    private val viewModel: MenuViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setupChannel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMenuBinding.inflate(layoutInflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserver()
        test()
    }

    private fun test() {
        viewModel.setStateEvent(MenuStateEvent.GetAllGames())
    }

    private fun initObserver() {
        viewModel.viewState.observe(viewLifecycleOwner,{viewState->
            viewState.gameList?.let{
                printLogD("MenuFragment" , "$it")
            }
        })
    }

    override fun initStateMessageCallback() {
        stateMessageCallback = object : StateMessageCallback {
            override fun removeMessageFromStack() {
                viewModel.clearAllStateMessages()
            }

        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}