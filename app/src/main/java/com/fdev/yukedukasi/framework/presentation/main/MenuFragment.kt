package com.fdev.yukedukasi.framework.presentation.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fdev.yukedukasi.databinding.FragmentLoginBinding
import com.fdev.yukedukasi.databinding.FragmentMenuBinding


class MenuFragment : MenuBaseFragment() {

    private var _binding : FragmentMenuBinding? = null

    private val binding
        get() = _binding!!


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMenuBinding.inflate(layoutInflater , container ,false)
        val view = binding.root
        return view
    }

    override fun initStateMessageCallback() {

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}