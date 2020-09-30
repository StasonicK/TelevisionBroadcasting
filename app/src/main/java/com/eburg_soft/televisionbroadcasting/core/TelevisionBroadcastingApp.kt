package com.eburg_soft.televisionbroadcasting.core

import android.app.Application
import com.eburg_soft.televisionbroadcasting.data.di.application.AppComponent
import com.eburg_soft.televisionbroadcasting.data.di.application.AppContextModule
import com.eburg_soft.televisionbroadcasting.data.di.application.DaggerAppComponent
import com.eburg_soft.televisionbroadcasting.data.di.application.DatabaseModule
import com.eburg_soft.televisionbroadcasting.data.di.application.NetworkModule
import com.github.ajalt.timberkt.Timber

class TelevisionBroadcastingApp : Application() {

    override fun onCreate() {
        super.onCreate()
        component.inject(this)
        initTimber()
    }

    val component: AppComponent by lazy {
        DaggerAppComponent.builder()
            .appContextModule(AppContextModule(this.applicationContext))
            .databaseModule(DatabaseModule())
            .networkModule(NetworkModule())
            .build()
    }

    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }
}