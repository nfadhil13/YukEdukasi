package com.fdev.yukedukasi.di

import com.fdev.yukedukasi.framework.datasource.network.apicall.services.GameApiService
import com.fdev.yukedukasi.framework.datasource.network.apicall.services.SiswaApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import retrofit2.Retrofit

@Module
@InstallIn(ActivityComponent::class)
object ActivityModule {

    @Provides
    fun provideSiswaApi(
        retrofitBuilder : Retrofit.Builder
    ) : SiswaApiService {
        return retrofitBuilder
                .build()
                .create(SiswaApiService::class.java)
    }

    @Provides
    fun provideGameApi(
            retrofitBuilder: Retrofit.Builder
    ) : GameApiService {
        return retrofitBuilder
                .build()
                .create(GameApiService::class.java)
    }

}