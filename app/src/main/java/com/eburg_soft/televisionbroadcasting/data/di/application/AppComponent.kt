package com.eburg_soft.televisionbroadcasting.data.di.application

import android.app.Application
import com.eburg_soft.televisionbroadcasting.data.di.tvmenu.TVMenuComponent
import com.eburg_soft.televisionbroadcasting.data.di.tvmenu.TVMenuContextModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppContextModule::class, NetworkModule::class, DatabaseModule::class, RepositoryModule::class, UseCaseModule::class])
interface AppComponent {

    fun createTVMenuComponent(tvMenuContextModule: TVMenuContextModule): TVMenuComponent

    fun inject(app: Application)
}