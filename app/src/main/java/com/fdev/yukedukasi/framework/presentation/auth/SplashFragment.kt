package com.fdev.yukedukasi.framework.presentation.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.navigation.fragment.findNavController
import com.fdev.yukedukasi.R
import com.fdev.yukedukasi.business.domain.model.Siswa
import com.fdev.yukedukasi.business.domain.model.User
import com.fdev.yukedukasi.databinding.FragmentSplashBinding
import com.fdev.yukedukasi.framework.presentation.auth.state.AuthStateEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

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

    private var hasEverRegister : Boolean = false

    private var isLoginAttempDone : Boolean = false


    private lateinit var currentSiswa : Siswa


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


    //Check last user in this phone
    private fun checkLastUser() {
        val lastUser = viewModel.checkLastUserLogIn()
        lastUser?.let { user ->
            hasEverRegister = true
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
                //Check if there is user in this phone
                if(hasEverRegister){
                    //Doing login attempt to server
                    if(isLoginAttempDone){
                        //If login attempt succes than log in
                        if(::currentSiswa.isInitialized){
                            login(currentSiswa)
                        }else{
                            //If login attempt fail than nav to log in
                            navToLogin()
                        }
                    }
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
                splashViewState.siswa?.let { siswa ->
                   if(fadeAnimation.hasEnded()){
                       login(siswa)
                   }else{
                       currentSiswa = siswa
                   }
                    isLoginAttempDone = true
                } ?: if (fadeAnimation.hasEnded()) navToLogin()
            }
        })
    }

    private fun login(siswa : Siswa){
        viewModel.sessionManager.login(newSiswa = siswa)
    }

    private fun navToLogin() {
        findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}