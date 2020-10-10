package com.fdev.yukedukasi.framework.presentation.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.fdev.yukedukasi.R
import com.fdev.yukedukasi.business.domain.model.User
import com.fdev.yukedukasi.business.interactors.auth.LogIn
import com.fdev.yukedukasi.databinding.ActivityAuthBinding
import com.fdev.yukedukasi.databinding.FragmentSplashBinding
import com.fdev.yukedukasi.framework.datasource.network.apicall.SiswaApiService
import com.fdev.yukedukasi.framework.presentation.auth.state.AuthStateEvent
import com.fdev.yukedukasi.util.SessionManager
import com.fdev.yukedukasi.util.printLogD
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class SplashFragment : AuthBaseFragment() {

    private var _binding: FragmentSplashBinding? = null

    private val binding
        get() = _binding!!


    private lateinit var topAnimation: Animation

    private lateinit var botAnimation: Animation

    private lateinit var fadeAnimation: Animation

    private var hasLogInBefore: Boolean = false

    private var isLoginAttempDone : Boolean = false


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserver()
        initUI()
        checkLastUser()
    }

    private fun checkLastUser() {
        val lastUser = viewModel.checkLastUserLogIn()
        lastUser?.let { user ->
            hasLogInBefore = true
            viewModel.setStateEvent(AuthStateEvent
                    .SyncStateEvent(
                            user = user
                    ))
        } ?: if (fadeAnimation.hasEnded()) navToLogin()
    }


    private fun initUI() {
        topAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.top_animation)
        botAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.bottom_animation)
        fadeAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)
        fadeAnimation.startOffset = botAnimation.duration
        fadeAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {
                if(!hasLogInBefore || isLoginAttempDone){
                    navToLogin()
                }
            }

            override fun onAnimationRepeat(animation: Animation?) {

            }

        })
        binding.apply {
            imagePerson.animation = botAnimation
            imageHat.animation = topAnimation
            appTv.animation = fadeAnimation
        }

    }


    private fun initObserver() {
        viewModel.viewState.observe(viewLifecycleOwner, { viewState ->
            viewState.splashViewState?.let { splashViewState ->
                isLoginAttempDone = true
                splashViewState.siswa?.let { siswa ->
                    viewModel.sessionManager.login(newSiswa = siswa)
                } ?: if (fadeAnimation.hasEnded()) navToLogin()
            }
        })
    }

    private fun navToLogin() {
        findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}