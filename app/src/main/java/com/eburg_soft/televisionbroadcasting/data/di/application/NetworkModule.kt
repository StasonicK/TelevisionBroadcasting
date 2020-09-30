package com.eburg_soft.televisionbroadcasting.data.di.application

import com.eburg_soft.televisionbroadcasting.data.datasource.network.TVApi
import com.eburg_soft.televisionbroadcasting.data.datasource.network.TVApiService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideApi(): TVApi = TVApiService.currencyApi()
}