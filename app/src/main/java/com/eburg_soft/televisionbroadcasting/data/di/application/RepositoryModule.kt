package com.eburg_soft.televisionbroadcasting.data.di.application

import com.eburg_soft.televisionbroadcasting.data.repository.TVRepository
import com.eburg_soft.televisionbroadcasting.data.repository.TVRepositoryImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun providesRepository(repository: TVRepositoryImpl): TVRepository
}