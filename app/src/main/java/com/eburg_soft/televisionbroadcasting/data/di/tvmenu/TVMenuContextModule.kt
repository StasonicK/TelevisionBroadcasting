package com.eburg_soft.televisionbroadcasting.data.di.tvmenu

import android.content.Context
import com.eburg_soft.televisionbroadcasting.data.di.base.scopes.PerScreen
import dagger.Module
import dagger.Provides

@Module
class TVMenuContextModule(private val context: Context) {

    @Provides
    @PerScreen
    fun provideContext(): Context = context
}