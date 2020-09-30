package com.eburg_soft.televisionbroadcasting.core

import android.app.Application
import com.eburg_soft.televisionbroadcasting.data.di.application.AppComponent
import com.eburg_soft.televisionbroadcasting.data.di.application.AppModule
import com.eburg_soft.televisionbroadcasting.data.di.application.DaggerAppComponent
import com.eburg_soft.televisionbroadcasting.data.di.application.DatabaseModule
import com.eburg_soft.televisionbroadcasting.data.di.application.NetworkModule

class TelevisionBroadcastingApp : Application() {

    override fun onCreate() {
        super.onCreate()
        component.inject(this)
    }

    val component: AppComponent by lazy {
        DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .databaseModule(DatabaseModule())
            .networkModule(NetworkModule())
            .build()
    }
}