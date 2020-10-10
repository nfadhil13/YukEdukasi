package com.fdev.yukedukasi.util

import java.lang.Exception


class ApiException(message : String) : Exception(message){

    override val cause: Throwable?
        get() = Throwable(
                message
        )

    init{
        printLogD("ApiException" , "Error : $message")
    }


}
