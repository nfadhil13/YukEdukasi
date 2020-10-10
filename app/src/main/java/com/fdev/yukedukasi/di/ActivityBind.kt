package com.fdev.yukedukasi.di

import com.fdev.yukedukasi.business.data.network.abstraction.SiswaNetworkDataSource
import com.fdev.yukedukasi.business.data.network.implementation.SiswaNetworkDataSourceImpl
import com.fdev.yukedukasi.framework.datasource.network.abstraction.SiswaNetworkService
import com.fdev.yukedukasi.framework.datasource.network.implementation.SiswaNetworkServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class ActivityBind {

    @Binds
    abstract fun bindSiswaNetworkService(siswaNetworkServiceImpl: SiswaNetworkServiceImpl) : SiswaNetworkService

    @Binds
    abstract fun bindSiswaNetworkDataSource(siswaNetworkDataSourceImpl: SiswaNetworkDataSourceImpl) : SiswaNetworkDataSource

}