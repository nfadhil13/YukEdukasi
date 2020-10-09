package com.fdev.yukedukasi.di

import android.content.Context
import android.content.SharedPreferences
import com.fdev.yukedukasi.framework.presentation.BaseApplication
import com.fdev.yukedukasi.util.Constants
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object AppModule {


    @JvmStatic
    @Singleton
    @Provides
    fun provideSharedPreferences(
            application: BaseApplication
    ): SharedPreferences {
        return application
                .getSharedPreferences(
                        Constants.APP_PREFENCES,
                        Context.MODE_PRIVATE
                )
    }




}