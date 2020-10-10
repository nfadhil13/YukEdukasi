package com.fdev.yukedukasi.framework.presentation

import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

fun TextView.changeTextcolor(
        @ColorRes color : Int
){
    context?.let{context ->
        setTextColor(ContextCompat.getColor(context , color))
    }
}

fun ProgressBar.show(isShow : Boolean){
    if(isShow){
        visibility = View.VISIBLE
    }else{
        visibility = View.INVISIBLE
    }
}