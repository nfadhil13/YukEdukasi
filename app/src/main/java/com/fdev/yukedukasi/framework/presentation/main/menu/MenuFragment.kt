package com.fdev.yukedukasi.framework.presentation.main.menu

import android.os.Bundle
import android.view.*
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.fdev.yukedukasi.R
import com.fdev.yukedukasi.business.domain.model.Game
import com.fdev.yukedukasi.business.domain.state.StateMessageCallback
import com.fdev.yukedukasi.databinding.FragmentMenuBinding
import com.fdev.yukedukasi.framework.presentation.main.MainBaseFragment
import com.fdev.yukedukasi.framework.presentation.main.menu.state.MenuStateEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview


@AndroidEntryPoint
@FlowPreview
@ExperimentalCoroutinesApi
class MenuFragment : MainBaseFragment(), GameListAdapter.Interaction {

    companion object {
        const val GAME_BUNDLE_KEY = "framework.presentation.main.menu.menufragment.bundle_key"
    }

    private var _binding: FragmentMenuBinding? = null

    private val binding
        get() = _binding!!

    private var _recyclerViewAdapter: GameListAdapter? = null

    private val recyclerViewAdapter
        get() = _recyclerViewAdapter!!

    private val viewModel: MenuViewModel by viewModels()


    private var _requestManager: RequestManager? = null

    private val requestManager
        get() = _requestManager!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setupChannel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMenuBinding.inflate(layoutInflater, container, false)
        val view = binding.root
        setHasOptionsMenu(true)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserver()
        initUI()
    }

    private fun initUI() {
        initGlide()
        _recyclerViewAdapter = GameListAdapter(
                requestManager,
                this
        )

        binding.apply {
            menuRecyclerviewContent.apply {
                val linearlayout = LinearLayoutManager(requireContext())
                layoutManager = linearlayout
                adapter = recyclerViewAdapter
            }
        }

        binding.toolbar?.inflateMenu(R.menu.main_menu)?.let {
            binding.toolbar?.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.log_out -> {
                        viewModel.logOut()

                    }
                }
                true
            }
        }
        getAllGame()
    }

    private fun getAllGame() {
        viewModel.setStateEvent(MenuStateEvent.GetAllGames())
    }

    private fun initObserver() {
        viewModel.viewState.observe(viewLifecycleOwner, { viewState ->
            viewState.gameList?.let {
                recyclerViewAdapter.submitList(it)
            }
        })

        viewModel.shouldDisplayProgressBar.observe(viewLifecycleOwner, {
            uiController.displayProgressBar(it)
        })

        viewModel.stateMessage.observe(viewLifecycleOwner, {
            it?.let {
                handleStateMessage(it, stateMessageCallback)
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


    private fun initGlide() {
        _requestManager = Glide.with(requireActivity())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding?.menuRecyclerviewContent?.adapter = null
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        _requestManager = null
        _recyclerViewAdapter = null


    }


    override fun onItemSelected(position: Int, item: Game) {
        val bundle = bundleOf(GAME_BUNDLE_KEY to item)
        findNavController().navigate(R.id.action_menuFragment_to_gamedetail_nav, bundle)
    }
}