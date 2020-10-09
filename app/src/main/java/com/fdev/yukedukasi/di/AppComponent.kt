package com.fdev.yukedukasi.di

import com.fdev.yukedukasi.framework.presentation.BaseApplication
import com.fdev.yukedukasi.framework.presentation.auth.AuthActivity
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import javax.inject.Singleton


@Singleton
@Component(
        modules = [
            AppModule::class
        ]
)
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: BaseApplication): AppComponent
    }

    fun inject(authActivity: AuthActivity)

}