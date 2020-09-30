package com.eburg_soft.televisionbroadcasting.data.di.application

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppContextModule(private val context: Context) {

    @Singleton
    @Provides
//    @AppContext
    fun provideContext(): Context = context
}