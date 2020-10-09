package com.fdev.yukedukasi.framework.presentation.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.navigation.fragment.findNavController
import com.fdev.yukedukasi.R
import com.fdev.yukedukasi.databinding.ActivityAuthBinding
import com.fdev.yukedukasi.databinding.FragmentSplashBinding
import com.fdev.yukedukasi.util.SessionManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : AuthBaseFragment(){

    private var _binding : FragmentSplashBinding? = null

    private val binding
        get() = _binding!!


    @Inject
    lateinit var sessionManager: SessionManager

    lateinit var topAnimation : Animation

    lateinit var botAnimation : Animation

    lateinit var fadeAnimation : Animation

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSplashBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserver()
        initUI()
    }

    private fun initUI() {
        topAnimation = AnimationUtils.loadAnimation(requireContext() , R.anim.top_animation)
        botAnimation = AnimationUtils.loadAnimation(requireContext() , R.anim.bottom_animation)
        fadeAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)
        fadeAnimation.startOffset = botAnimation.duration
        binding.apply {
            imagePerson.animation = botAnimation
            imageHat.animation = topAnimation
            appTv.animation = fadeAnimation
        }
    }

    private fun initObserver() {
        val lastUser = sessionManager.checkLastUserLogIn()
        lastUser?.let {
        }
    }

    private fun navToLogin() {
        findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding  = null
    }

}