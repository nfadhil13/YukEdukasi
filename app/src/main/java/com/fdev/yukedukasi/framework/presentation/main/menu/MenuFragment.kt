package com.fdev.yukedukasi.framework.presentation.main.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.fdev.yukedukasi.R
import com.fdev.yukedukasi.business.domain.model.Game
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
class MenuFragment : MainBaseFragment() , GameListAdapter.Interaction {

    companion object{
        const val GAME_BUNDLE_KEY = "framework.presentation.main.menu.menufragment.bundle_key"
    }

    private var _binding: FragmentMenuBinding? = null

    private val binding
        get() = _binding!!

    lateinit var recyclerViewAdapter : GameListAdapter

    private val viewModel: MenuViewModel by viewModels()


    private var _requestManager : RequestManager? = null

    private val requestManager
        get() = _requestManager!!

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
        initUI()
        test()
    }

    private fun initUI() {
        initGlide()
        recyclerViewAdapter = GameListAdapter(
                requestManager,
                this
        )

        binding.apply {
            menuRecyclerviewContent.apply {
                val gridLayoutManager = GridLayoutManager(requireContext() , 2)
                layoutManager = gridLayoutManager
                setHasFixedSize(true)
                adapter = recyclerViewAdapter
            }
        }
    }

    private fun test() {
        viewModel.setStateEvent(MenuStateEvent.GetAllGames())
    }

    private fun initObserver() {
        viewModel.viewState.observe(viewLifecycleOwner,{viewState->
            viewState.gameList?.let{
                recyclerViewAdapter.submitList(it)
            }
        })

        viewModel.shouldDisplayProgressBar.observe(viewLifecycleOwner , {
            uiController.displayProgressBar(it)
        })
    }

    override fun initStateMessageCallback() {
        stateMessageCallback = object : StateMessageCallback {
            override fun removeMessageFromStack() {
                viewModel.clearAllStateMessages()
            }

        }

    }

    private fun initGlide() {
            _requestManager = Glide.with(requireActivity())
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding?.menuRecyclerviewContent?.adapter = null
        _binding = null
        _requestManager = null
    }

    override fun onItemSelected(position: Int, item: Game) {
        val bundle = bundleOf(GAME_BUNDLE_KEY to item)
        findNavController().navigate(R.id.action_menuFragment_to_materiFragment , bundle)
    }
}