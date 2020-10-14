package com.fdev.yukedukasi.framework.presentation

import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat

fun TextView.changeTextcolor(
        @ColorRes color : Int
){
    context?.let{context ->
        setTextColor(ContextCompat.getColor(context , color))
    }
}


fun Button.changeBackground(
        @DrawableRes drawable : Int
){
    context?.let{context ->
        setBackground(ContextCompat.getDrawable(context ,  drawable))
    }
}

fun View.show(isShow : Boolean){
    if(isShow){
        visibility = View.VISIBLE
    }else{
        visibility = View.INVISIBLE
    }
}