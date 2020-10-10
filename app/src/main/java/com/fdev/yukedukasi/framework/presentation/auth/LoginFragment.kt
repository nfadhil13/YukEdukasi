package com.fdev.yukedukasi.framework.presentation.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fdev.yukedukasi.R
import com.fdev.yukedukasi.business.domain.model.User
import com.fdev.yukedukasi.business.domain.state.StateMessage
import com.fdev.yukedukasi.business.domain.state.StateMessageCallback
import com.fdev.yukedukasi.databinding.FragmentLoginBinding
import com.fdev.yukedukasi.framework.presentation.auth.state.AuthStateEvent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
class LoginFragment : AuthBaseFragment(){

    private var _binding : FragmentLoginBinding? = null

    private val binding
    get() = _binding!!


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentLoginBinding.inflate(layoutInflater , container ,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserver()
        initUI()
    }

    private fun initUI() {
        binding.apply {

            btnLogin.setOnClickListener {
               validateInput()?.let{ user ->
                   login(user)
               }

            }
        }
    }

    private fun login(user: User) {
        viewModel.setStateEvent(AuthStateEvent.LoginStateEvent(user))
    }

    private fun validateInput() : User?{
        var user : User? = null
        binding.apply {
            textInputNis.editText?.let{nisEditText ->
                if(nisEditText.text.isEmpty()){
                    textInputNis.error = getString(R.string.nis_empty_error)
                }else{
                    textInputNis.error = null
                    textInputPin.editText?.let{pinEditText ->
                        if(pinEditText.text.isEmpty()){
                            textInputPin.error = getString(R.string.pin_empty_error)
                        }else{
                            textInputNis.error = null
                            user = User(nisEditText.text.toString() , pinEditText.text.toString())
                        }
                    }
                }
            }
        }
        return user
    }



    private fun initObserver() {
        viewModel.viewState.observe(viewLifecycleOwner , { viewState->
            viewState.loginViewState?.siswa?.let { siswa ->
                viewModel.logIn(siswa)
            }
        })
    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}