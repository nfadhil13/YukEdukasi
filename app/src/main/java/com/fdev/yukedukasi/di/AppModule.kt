package com.fdev.yukedukasi.di

import android.content.Context
import android.content.SharedPreferences
import com.fdev.yukedukasi.framework.presentation.BaseApplication
import com.fdev.yukedukasi.util.Constants
import com.fdev.yukedukasi.util.SessionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {


    @Singleton
    @Provides
    fun provideSharedPreferences(
            @ApplicationContext context : Context
    ): SharedPreferences {
        return context
                .getSharedPreferences(
                        Constants.APP_PREFENCES,
                        Context.MODE_PRIVATE
                )
    }

    @Singleton
    @Provides
    fun provideSharedPreferencesEditor(
            sharedPreferences: SharedPreferences
    ): SharedPreferences.Editor {
        return sharedPreferences.edit()
    }








}