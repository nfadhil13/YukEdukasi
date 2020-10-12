package com.fdev.yukedukasi.framework.presentation.main.gamedetail.modechoice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.fdev.yukedukasi.R
import com.fdev.yukedukasi.business.domain.model.Game
import com.fdev.yukedukasi.databinding.FragmentModechoiceBinding
import com.fdev.yukedukasi.framework.presentation.main.gamedetail.GameDetailBaseFragment
import com.fdev.yukedukasi.framework.presentation.main.gamedetail.state.GameDetailStateEvent
import com.fdev.yukedukasi.framework.presentation.main.menu.MenuFragment
import com.fdev.yukedukasi.framework.presentation.show
import com.fdev.yukedukasi.util.printLogD
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@AndroidEntryPoint
@ExperimentalCoroutinesApi
@FlowPreview
class ModeChoiceFragment : GameDetailBaseFragment() {

    private var _binding : FragmentModechoiceBinding? = null

    private val binding
    get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding =
                FragmentModechoiceBinding.inflate(inflater , container , false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.get(MenuFragment.GAME_BUNDLE_KEY)?.let {
            if (it is Game) {
                initFragment(it)
            } else {

            }
        }
    }

    private fun initFragment(choosenGame: Game) {
        initUI(choosenGame)
        showButton(false)
        setCurrentGame(choosenGame)
        showButton(true)
        test(choosenGame)
    }

    private fun test(game: Game) {
        viewModel.viewState.observe(viewLifecycleOwner , Observer {
            printLogD("Test", "${it.testViewState}")
        })
        viewModel.setStateEvent(GameDetailStateEvent.GetTestOfGame(game))
    }

    private fun initUI(game : Game) {
        binding.apply {
            cardViewTest.setOnClickListener {
                navToTest()
            }
            cardViewMateri.setOnClickListener {
                navToMateri()
            }
            title.text = game.name
        }
    }

    private fun navToTest() {
        findNavController().navigate(R.id.action_modeChoiceFragment_to_testFragment)
    }

    private fun navToMateri() {
        findNavController().navigate(R.id.action_modeChoiceFragment_to_materiFragment)
    }

    private fun setCurrentGame(game: Game) {
        viewModel.setCurrentGame(game)
    }

    private fun showButton(isShow : Boolean) {
        binding.apply{
            cardViewMateri.show(isShow)
            cardViewTest.show(isShow)
        }
    }





    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.reset()
        _binding = null
    }

}