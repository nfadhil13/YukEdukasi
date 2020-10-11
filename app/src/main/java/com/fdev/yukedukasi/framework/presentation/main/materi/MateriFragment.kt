package com.fdev.yukedukasi.framework.presentation.main.materi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.fdev.yukedukasi.R
import com.fdev.yukedukasi.business.domain.model.Game
import com.fdev.yukedukasi.business.domain.model.Materi
import com.fdev.yukedukasi.business.domain.state.StateMessageCallback
import com.fdev.yukedukasi.business.domain.state.UIComponentType
import com.fdev.yukedukasi.databinding.FragmentMateriBinding
import com.fdev.yukedukasi.framework.presentation.main.MainBaseFragment
import com.fdev.yukedukasi.framework.presentation.main.materi.state.MateriStateEvent
import com.fdev.yukedukasi.framework.presentation.main.menu.MenuFragment
import com.fdev.yukedukasi.util.OneTimePlayerManager
import com.fdev.yukedukasi.util.printLogD
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch


@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MateriFragment : MainBaseFragment(), MaterListAdapter.Interaction {


    private var _binding: FragmentMateriBinding? = null

    private val binding
        get() = _binding!!

    private val viewModel: MateriViewModel by viewModels()


    private var _requestManager: RequestManager? = null

    private val requestManager
        get() = _requestManager!!

    private lateinit var materiListAdapter: MaterListAdapter


    private lateinit var player: OneTimePlayerManager

    private lateinit var currentMateri: Materi


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMateriBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.get(MenuFragment.GAME_BUNDLE_KEY)?.let {
            if (it is Game) {
                initPlayer()
                initObserver()
                initUI()
                getMateri(it)
            } else {
                showErrorMessage()
            }
        } ?: showErrorMessage()

    }



    private fun showErrorMessage() {
        uiController.basicUIInteraction(
                uiComponentType = UIComponentType.Dialog(),
                message = getString(R.string.change_fragment_fail_message)
        )
        findNavController().navigate(R.id.menuFragment)
    }

    private fun getMateri(game: Game) {
        viewModel.setStateEvent(MateriStateEvent.GetMateriOfGame(game))
    }

    private fun initUI() {
        initGlide()
        materiListAdapter = MaterListAdapter(
                requestManager,
                this
        )
        binding.apply {
            recyclerView.apply {
                val linearLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                layoutManager = linearLayoutManager
                adapter = materiListAdapter
            }
            imageviewPlaysound.setOnClickListener {
                player.playMusic()
            }
        }
    }

    private fun initObserver() {
        viewModel.viewState.observe(viewLifecycleOwner, { viewState ->
            printLogD("MateriFragment" , "There is view state $viewState")
            viewState.currentGame?.gameMateri?.let {
                changeCurrentSelectedMateri(it[0])
                player.prepareMusic(it[0].sound)
                materiListAdapter.submitList(it)
            }
        })

        viewModel.shouldDisplayProgressBar.observe(viewLifecycleOwner, {
            uiController.displayProgressBar(it)
        })

    }

    private fun initPlayer() {
        player = OneTimePlayerManager(requireContext())
    }

    private fun changeCurrentSelectedMateri(materi: Materi) {
        currentMateri = materi
        binding.tvMateriName.text = materi.name
        requestManager
                .load(materi.image)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .error(R.drawable.ic_round_error_outline_24)
                .into(binding.imageviewSelectedMateri)
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
        player.releasePlayer()
        _requestManager = null
        _binding?.recyclerView?.adapter = null
        _binding = null
    }


    override fun onPause() {
        super.onPause()
        player.pausePlayer()
    }


    private fun initGlide() {
        _requestManager = Glide.with(requireActivity())
    }

    override fun onItemSelected(position: Int, item: Materi) {
        changeCurrentSelectedMateri(item)
        player.prepareMusic(item.sound)
    }

}