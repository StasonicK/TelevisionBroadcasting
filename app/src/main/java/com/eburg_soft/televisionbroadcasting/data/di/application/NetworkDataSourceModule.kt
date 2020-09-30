package com.eburg_soft.televisionbroadcasting.data.di.application

import com.eburg_soft.televisionbroadcasting.core.TVNetworkDataSource
import com.eburg_soft.televisionbroadcasting.data.datasource.network.networkdatasource.TVNetworkDataSourceImpl
import com.eburg_soft.televisionbroadcasting.data.datasource.network.networkdatasource.TVNetworkDataSourceImplTest
import com.eburg_soft.televisionbroadcasting.data.di.context.ReleaseContext
import com.eburg_soft.televisionbroadcasting.data.di.context.TestContext
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface NetworkDataSourceModule {

    @Singleton
    @Binds
    @ReleaseContext
    fun provideReleaseNetworkDataSource(dataSource: TVNetworkDataSourceImpl):TVNetworkDataSource

    @Singleton
    @Binds
    @TestContext
    fun provideTestNetworkDataSource(dataSource: TVNetworkDataSourceImplTest):TVNetworkDataSource
}