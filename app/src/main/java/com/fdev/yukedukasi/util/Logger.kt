package com.fdev.yukedukasi.util

import android.util.Log
import com.fdev.yukedukasi.util.Constants.DEBUG
import com.fdev.yukedukasi.util.Constants.TAG

var isUnitTest = false

fun printLogD(className: String?, message: String ) {
    if (DEBUG && !isUnitTest) {
        Log.d(TAG, "$className: $message")
    }
    else if(DEBUG && isUnitTest){
        println("$className: $message")
    }
}

/*
    Priorities: Log.DEBUG, Log. etc....
 */
