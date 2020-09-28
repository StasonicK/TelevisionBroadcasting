package com.eburg_soft.televisionbroadcasting.core

import android.app.Application

class TelevisionBroadcastingApp : Application() {

    override fun onCreate() {
        super.onCreate()
//        component.inject(this)
    }

//    val component: AppComponent by lazy {
//        DaggerAppComponent.builder()
//            .appContextModule(AppContextModule(applicationContext))
//            .build()
//    }
}