package com.eburg_soft.televisionbroadcasting.data.di.application

import android.app.Application
import android.content.Context
import com.eburg_soft.televisionbroadcasting.core.TelevisionBroadcastingApp
import dagger.Module
import dagger.Provides

@Module
class AppModule(private val application: TelevisionBroadcastingApp) {

    @Provides
    fun provideApplication(): Application = application

    @Provides
    fun provideContext(): Context = application.applicationContext
}