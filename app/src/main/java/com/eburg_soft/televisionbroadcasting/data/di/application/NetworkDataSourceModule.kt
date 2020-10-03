package com.eburg_soft.televisionbroadcasting.data.di.application

import com.eburg_soft.televisionbroadcasting.core.Constants
import com.eburg_soft.televisionbroadcasting.core.TVNetworkDataSource
import com.eburg_soft.televisionbroadcasting.data.datasource.network.networkdatasource.TVNetworkDataSourceImpl
import com.eburg_soft.televisionbroadcasting.data.datasource.network.networkdatasource.TVNetworkDataSourceImplTest
import dagger.Binds
import dagger.Module
import javax.inject.Named
import javax.inject.Singleton

@Module
interface NetworkDataSourceModule {

    @Singleton
    @Binds
    @Named(Constants.PRODUCTIVE_VERSION)
    fun provideProductiveNetworkDataSource(dataSource: TVNetworkDataSourceImpl): TVNetworkDataSource

    @Singleton
    @Binds
    @Named(Constants.TEST_VERSION)
    fun provideTestNetworkDataSource(dataSource: TVNetworkDataSourceImplTest): TVNetworkDataSource
}