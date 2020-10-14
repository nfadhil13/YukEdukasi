package com.fdev.yukedukasi.framework.presentation.main.gamedetail.test

import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.fdev.yukedukasi.R
import com.fdev.yukedukasi.business.domain.model.Answer
import com.fdev.yukedukasi.business.domain.model.Soal
import com.fdev.yukedukasi.business.domain.state.AreYouSureCallback
import com.fdev.yukedukasi.business.domain.state.StateMessage
import com.fdev.yukedukasi.business.domain.state.StateMessageCallback
import com.fdev.yukedukasi.business.domain.state.UIComponentType
import com.fdev.yukedukasi.business.interactors.main.gamedetail.UpdateTestScore
import com.fdev.yukedukasi.databinding.FragmentTestBinding
import com.fdev.yukedukasi.framework.presentation.main.gamedetail.GameDetailBaseFragment
import com.fdev.yukedukasi.framework.presentation.main.gamedetail.state.GameDetailStateEvent
import com.fdev.yukedukasi.util.OneTimePlayerManager
import com.fdev.yukedukasi.util.printLogD
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ExperimentalCoroutinesApi
@FlowPreview
class TestFragment : GameDetailBaseFragment(), SoalListAdapter.Interaction {

    private var _binding: FragmentTestBinding? = null

    private val binding
        get() = _binding!!

    private lateinit var listAdapter: SoalListAdapter


    private lateinit var player: OneTimePlayerManager

    private var _soundPool: SoundPool? = null

    private val soundPool
        get() = _soundPool!!

    private var rightSound = -1

    private var wrongSound = -1

    private var currentScore = 0

    private var lastPosition = 0

    private var soalTotal = 0

    private var listAnswer = ArrayList<Answer>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val callback = object : OnBackPressedCallback(true) {

            override fun handleOnBackPressed() {
                uiController.basicUIInteraction(
                        getString(R.string.test_back_warn),
                        UIComponentType.AreYouSureDialog(object : AreYouSureCallback {
                            override fun proceed() {
                                findNavController().navigate(R.id.action_testFragment_to_modeChoiceFragment)
                                viewModel.resetTest()
                            }

                            override fun cancel() {
                                // Do nothing
                            }

                        })
                )
                //Ask user if they are sure to get back , if sure than get back

            }


        }

        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentTestBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserver()
        getTest()
    }

    private fun initObserver() {
        viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->
            viewState.testViewState?.soalList?.let {
                initUI(it)
            }

            viewState.testViewState?.lastPostition?.let{
                lastPosition = it
            }

            viewState.testViewState?.answerList?.let{
                listAnswer.addAll(it)
            }
        })

    }

    private fun initUI(soalList: List<Soal>) {

        soalTotal = soalList.size
        initSoundPool()
        initPlayer()
        initGlide()
        listAdapter = SoalListAdapter(requestManager, this)
        binding.apply {

            progressbarSoal.max = soalTotal

            contentViewpager.apply {
                adapter = listAdapter
                offscreenPageLimit = 1
                isUserInputEnabled = false
            }
        }
        listAdapter.submitList(soalList)
        player.prepareMusics(listOf(soalList[0].soundPrefix, soalList[0].sound))
        binding.contentViewpager.setCurrentItem(lastPosition)
    }

    private fun initSoundPool() {
        val audioAttributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build()

        _soundPool = SoundPool.Builder()
                .setMaxStreams(2)
                .setAudioAttributes(audioAttributes)
                .build()

        rightSound = soundPool.load(requireContext(), R.raw.right_answer, 1)
        wrongSound = soundPool.load(requireContext(), R.raw.wrong_answer, 1)
    }


    private fun initPlayer() {
        player = OneTimePlayerManager(requireContext())
    }

    override fun handleStateMessage(stateMessage: StateMessage, stateMessageCallback: StateMessageCallback) {
        var newCallback : StateMessageCallback? = null
        stateMessage.response.message?.contains(UpdateTestScore.SUBMIT_SCORE_SUCCESS)?.let{
            if(it){
                 newCallback = object : StateMessageCallback {
                    override fun removeMessageFromStack() {
                        viewModel.clearAllStateMessages()
                        findNavController().navigate(R.id.action_testFragment_to_modeChoiceFragment)
                        viewModel.resetTest()
                    }

                }
            }
        }
        newCallback?.let{
            super.handleStateMessage(stateMessage, it)
        }?: super.handleStateMessage(stateMessage, stateMessageCallback)

    }

    private fun getTest() {
        viewModel.getCurrentGame()?.let {game->
            viewModel.getCurrentSiswa()?.let{ siswa ->
                viewModel.setStateEvent(GameDetailStateEvent.GetTestOfGame(game , siswa.siswaId.toInt()))
            } ?: showErrorMessage()
        } ?: showErrorMessage()

    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding.contentViewpager.adapter = null
        _binding = null
        soundPool.release()
        player.releasePlayer()
        _soundPool = null
    }

    override fun onAnswerSumbited(isTrue: Boolean, position: Int , answer: Answer) {
        printLogD("TestFragment", "$isTrue , $position")
        listAnswer.add(answer)
        val message = if (isTrue) getString(R.string.right_answer_label)
        else getString(R.string.wrong_answer_label)
        if (isTrue) {
            currentScore = currentScore +1
            soundPool.play(rightSound, 0.5f, 0.5f, 1, 0, 0.99f)
        } else {
            soundPool.play(wrongSound, 0.5f, 0.5f, 1, 0, 0.99f)
        }

    }

    override fun playSound(soal: Soal) {
        player.playMusics(listOf(soal.soundPrefix, soal.sound))
    }

    override fun goToNextPage(currrentPosition: Int) {
        printLogD("TestFragment" , " sekarang ada di $currrentPosition menuju $soalTotal")
        if(currrentPosition+1 < soalTotal){
            binding.contentViewpager.setCurrentItem(currrentPosition + 1, true)
            lastPosition = currrentPosition+1
            binding.progressbarSoal.progress = lastPosition
        }else{
            submitTest()
        }


    }

    private fun submitTest() {
        printLogD("TestFragment" , "$currentScore , $soalTotal , ${getScore()}")
        viewModel.setStateEvent(GameDetailStateEvent.SubmitTest(getScore() , listAnswer))
    }

    private fun getScore() : Int{
        return (currentScore.toFloat()/soalTotal.toFloat() * 100f).toInt()
    }

    override fun onDestroy() {
        super.onDestroy()
        //Prevent rotate lost state
        saveTestState()
    }

    private fun saveTestState() {
        viewModel.saveTestLastState(currentScore , lastPosition , listAnswer)
    }


}