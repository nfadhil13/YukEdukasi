package com.fdev.yukedukasi.framework.presentation.auth

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import com.fdev.yukedukasi.databinding.ActivityAuthBinding
import com.fdev.yukedukasi.framework.presentation.BaseActivity
import com.fdev.yukedukasi.framework.presentation.main.MainActivity
import com.fdev.yukedukasi.framework.presentation.show
import com.fdev.yukedukasi.util.SessionManager
import com.fdev.yukedukasi.util.printLogD
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AuthActivity : BaseActivity() {

    private var _binding : ActivityAuthBinding? = null

    private val binding
    get() = _binding!!


    @Inject
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        //Setting up view
        super.onCreate(savedInstanceState)
        _binding = ActivityAuthBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //Initalize activity UI and observer
        init()
    }

    private fun init() {
        initObserver()
    }

    private fun initObserver() {
        //Init User Observer for user session
        sessionManager.currentUser.observe(this , Observer { siswa ->
            siswa?.let{
                // Go to main activity
                goToMainActivity()

            }
        })
    }

    private fun goToMainActivity() {
        val intent = Intent(this , MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun displayProgressBar(isDisplayed: Boolean) {
        binding.mainProgressbar.show(isDisplayed)
    }
}