package com.fdev.yukedukasi.framework.presentation.main.gamedetail.test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fdev.yukedukasi.databinding.FragmentMateriBinding
import com.fdev.yukedukasi.databinding.FragmentTestBinding
import com.fdev.yukedukasi.framework.presentation.main.gamedetail.GameDetailBaseFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ExperimentalCoroutinesApi
@FlowPreview
class TestFragment  : GameDetailBaseFragment(){

    private var _binding: FragmentTestBinding? = null

    private val binding
        get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentTestBinding.inflate(inflater, container , false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}