package com.fdev.yukedukasi.framework.presentation.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
abstract class AuthBaseFragment : Fragment(){


    val viewModel : AuthViewModel by activityViewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subcribeObserver()
    }

    private fun subcribeObserver() {
        viewModel.shouldDisplayProgressBar.observe(viewLifecycleOwner , Observer {

        })
    }

}