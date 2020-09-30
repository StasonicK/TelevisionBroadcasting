package com.eburg_soft.televisionbroadcasting.data.di.application

import android.app.Application
import com.eburg_soft.televisionbroadcasting.data.di.tvmenu.TVMenuComponent
import com.eburg_soft.televisionbroadcasting.data.di.tvmenu.TVMenuModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class, DatabaseModule::class])
interface AppComponent {

    fun createTVMenuComponent(tvMenuModule: TVMenuModule): TVMenuComponent

    fun app(app: Application)
}