package com.fdev.yukedukasi.framework.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fdev.yukedukasi.R
import com.fdev.yukedukasi.databinding.ActivityAuthBinding
import com.fdev.yukedukasi.databinding.ActivityMainBinding
import com.fdev.yukedukasi.framework.presentation.BaseActivity
import com.fdev.yukedukasi.framework.presentation.show

class MainActivity : BaseActivity() {

    private var _binding : ActivityMainBinding? = null

    private val binding
        get() = _binding!!
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    override fun displayProgressBar(isDisplayed: Boolean) {
        binding.mainProgressbar.show(isDisplayed)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}