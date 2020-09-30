package com.eburg_soft.televisionbroadcasting.data.di.application

import android.app.Application
import com.eburg_soft.televisionbroadcasting.data.datasource.database.DatabaseService
import com.eburg_soft.televisionbroadcasting.data.datasource.database.TVDatabase
import com.eburg_soft.televisionbroadcasting.data.datasource.database.daos.ChannelDao
import com.eburg_soft.televisionbroadcasting.data.datasource.database.daos.GroupDao
import com.eburg_soft.televisionbroadcasting.data.datasource.database.daos.ProgramDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule(val application: Application) {

    @Singleton
    @Provides
    fun provideDatabase(): TVDatabase = DatabaseService.createDatabase(application.applicationContext)

    @Singleton
    @Provides
    fun provideGroupDao(tvDatabase: TVDatabase): GroupDao = tvDatabase.groupDao()

    @Singleton
    @Provides
    fun provideChannelDao(tvDatabase: TVDatabase): ChannelDao = tvDatabase.channelDao()

    @Singleton
    @Provides
    fun provideProgramDao(tvDatabase: TVDatabase): ProgramDao = tvDatabase.programDao()
}