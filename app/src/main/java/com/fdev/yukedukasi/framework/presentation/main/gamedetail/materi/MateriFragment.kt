package com.fdev.yukedukasi.framework.presentation.main.gamedetail.materi

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
import com.fdev.yukedukasi.R
import com.fdev.yukedukasi.business.domain.model.Game
import com.fdev.yukedukasi.business.domain.model.Materi
import com.fdev.yukedukasi.business.domain.state.StateMessageCallback
import com.fdev.yukedukasi.business.domain.state.UIComponentType
import com.fdev.yukedukasi.databinding.FragmentMateriBinding
import com.fdev.yukedukasi.framework.presentation.main.MainBaseFragment
import com.fdev.yukedukasi.framework.presentation.main.gamedetail.GameDetailBaseFragment
import com.fdev.yukedukasi.framework.presentation.main.gamedetail.GameDetailViewModel
import com.fdev.yukedukasi.framework.presentation.main.gamedetail.state.GameDetailStateEvent
import com.fdev.yukedukasi.framework.presentation.main.menu.MenuFragment
import com.fdev.yukedukasi.util.OneTimePlayerManager
import com.fdev.yukedukasi.util.printLogD
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview


@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MateriFragment : GameDetailBaseFragment(), MaterListAdapter.Interaction {


    private var _binding: FragmentMateriBinding? = null

    private val binding
        get() = _binding!!


    private var _materiListAdapter: MaterListAdapter? = null

    private val materiListAdapter
        get() = _materiListAdapter!!


    private lateinit var player: OneTimePlayerManager

    private lateinit var currentMateri : Materi



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMateriBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPlayer()
        initObserver()
        initUI()
        viewModel.getCurrentGame()?.let{currentGame ->
            printLogD("MateriFragment","$currentGame")
            getMateri(currentGame)
        }

    }



    private fun showErrorMessage() {
        uiController.basicUIInteraction(
                uiComponentType = UIComponentType.Dialog(),
                message = getString(R.string.change_fragment_fail_message)
        )
        findNavController().navigate(R.id.menuFragment)
    }

    private fun getMateri(game: Game) {
        viewModel.setStateEvent(GameDetailStateEvent.GetMateriOfGame(game))
        printLogD("MateriFragment","retrieve dat")
    }

    private fun initUI() {
        initGlide()
        _materiListAdapter = MaterListAdapter(
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
            viewState.materiViewState?.let{ materiViewState ->
                materiViewState.currentMateri?.let{
                    changeCurrentSelectedMateri(it)
                }
                materiViewState.materiList.let{
                    materiListAdapter.submitList(it)
                }

            }
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
        player.prepareMusic(materi.sound)
    }



    override fun onDestroyView() {
        super.onDestroyView()
        saveMateriState()
        _binding?.recyclerView?.adapter = null
        _binding = null
    }

    private fun saveMateriState() {
        viewModel.setCurrentMateri(currentMateri)
    }

    override fun onDestroy() {
        super.onDestroy()
        player.releasePlayer()
        _materiListAdapter = null
    }


    override fun onPause() {
        super.onPause()
        player.pausePlayer()
    }




    override fun onItemSelected(position: Int, item: Materi) {
        changeCurrentSelectedMateri(item)
    }

}