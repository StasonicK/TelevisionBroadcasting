package com.eburg_soft.televisionbroadcasting.core

import android.app.Application
import com.eburg_soft.televisionbroadcasting.BuildConfig
import com.eburg_soft.televisionbroadcasting.data.di.application.AppComponent
import com.eburg_soft.televisionbroadcasting.data.di.application.AppContextModule
import com.eburg_soft.televisionbroadcasting.data.di.application.DaggerAppComponent
import com.eburg_soft.televisionbroadcasting.data.di.application.DatabaseModule
import com.eburg_soft.televisionbroadcasting.data.di.application.NetworkModule
import com.facebook.stetho.Stetho
import com.github.ajalt.timberkt.Timber

class TelevisionBroadcastingApp : Application() {

    override fun onCreate() {
        super.onCreate()
        component.inject(this)
        initTimber()
        initStetho()
    }

    private fun initStetho() {
        // Create an InitializerBuilder
        val initializerBuilder = Stetho.newInitializerBuilder(this)

        // Enable Chrome DevTools
        initializerBuilder.enableWebKitInspector(
            Stetho.defaultInspectorModulesProvider(this)
        )

        // Enable command line interface
        initializerBuilder.enableDumpapp(
            Stetho.defaultDumperPluginsProvider(this)
        )

        // Use the InitializerBuilder to generate an Initializer
        val initializer = initializerBuilder.build()

        // Initialize Stetho with the Initializer
        Stetho.initialize(initializer)
    }

    val component: AppComponent by lazy {
        DaggerAppComponent.builder()
            .appContextModule(AppContextModule(this.applicationContext))
            .databaseModule(DatabaseModule())
            .networkModule(NetworkModule())
            .build()
    }

    private fun initTimber() {
//        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
//        }
    }
}