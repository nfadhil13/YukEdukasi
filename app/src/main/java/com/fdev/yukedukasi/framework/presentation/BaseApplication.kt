package com.fdev.yukedukasi.framework.presentation

import android.app.Application
import com.fdev.yukedukasi.di.AppComponent
import com.fdev.yukedukasi.di.DaggerAppComponent


class BaseApplication  : Application() {

    lateinit var appComponent: AppComponent


    override fun onCreate() {
        super.onCreate()
        initAppComponent()
    }

    fun initAppComponent(){
        appComponent = DaggerAppComponent
                .factory()
                .create(this)
    }

}