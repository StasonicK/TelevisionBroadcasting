package com.eburg_soft.televisionbroadcasting.data.di.application

import android.app.Application
import android.content.Context
import com.eburg_soft.televisionbroadcasting.data.di.base.AppContext
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {

    @Singleton
    @Provides
//    @AppContext
    fun provideContext(): Context = context
}