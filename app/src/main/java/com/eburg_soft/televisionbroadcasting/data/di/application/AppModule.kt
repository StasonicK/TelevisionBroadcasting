package com.eburg_soft.televisionbroadcasting.data.di.application

import android.app.Application
import android.content.Context
import com.eburg_soft.televisionbroadcasting.core.TelevisionBroadcastingApp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val application: Application) {

    @Singleton
    @Provides
    fun provideApplication(): Application = application

    @Singleton
    @Provides
    fun provideContext(): Context = application.applicationContext
}