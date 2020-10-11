package com.eburg_soft.televisionbroadcasting.data.di.application

import com.eburg_soft.televisionbroadcasting.data.repository.TVRepository
import com.eburg_soft.televisionbroadcasting.domain.usecases.FetchChannelsFromApiIntoDbUseCase
import com.eburg_soft.televisionbroadcasting.domain.usecases.FetchDaysFromApiIntoDbUseCase
import com.eburg_soft.televisionbroadcasting.domain.usecases.FetchGroupsFromApiIntoDbUseCase
import com.eburg_soft.televisionbroadcasting.domain.usecases.FetchProgramsFromApiIntoDbUseCase
import com.eburg_soft.televisionbroadcasting.domain.usecases.GetAllDaysFromDbUseCase
import com.eburg_soft.televisionbroadcasting.domain.usecases.GetAllGroupsFromDbUseCase
import com.eburg_soft.televisionbroadcasting.domain.usecases.GetChannelsByGroupIdFromDbUseCase
import com.eburg_soft.televisionbroadcasting.domain.usecases.GetProgramsByChannelIdFromDbUseCase
import com.eburg_soft.televisionbroadcasting.domain.usecases.RemoveAllChannelsUseCase
import com.eburg_soft.televisionbroadcasting.domain.usecases.RemoveAllDaysUseCase
import com.eburg_soft.televisionbroadcasting.domain.usecases.RemoveAllGroupsUseCase
import com.eburg_soft.televisionbroadcasting.domain.usecases.RemoveAllProgramsUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UseCaseModule {

    @Singleton
    @Provides
    fun provideGetAllGroupsFromDbUseCase(repository: TVRepository): GetAllGroupsFromDbUseCase =
        GetAllGroupsFromDbUseCase(repository)

    @Singleton
    @Provides
    fun provideGetChannelsByGroupIdFromDbUseCase(repository: TVRepository): GetChannelsByGroupIdFromDbUseCase =
        GetChannelsByGroupIdFromDbUseCase(repository)

    @Singleton
    @Provides
    fun provideGetProgramsByChannelIdFromDbUseCase(repository: TVRepository): GetProgramsByChannelIdFromDbUseCase =
        GetProgramsByChannelIdFromDbUseCase(repository)

    @Singleton
    @Provides
    fun provideGetAllDaysFromDbUseCase(repository: TVRepository): GetAllDaysFromDbUseCase =
        GetAllDaysFromDbUseCase(repository)

    @Singleton
    @Provides
    fun provideFetchGroupsFromApiToDbUseCase(repository: TVRepository): FetchGroupsFromApiIntoDbUseCase =
        FetchGroupsFromApiIntoDbUseCase(repository)

    @Singleton
    @Provides
    fun provideFetchChannelsFromApiToDbUseCase(repository: TVRepository): FetchChannelsFromApiIntoDbUseCase =
        FetchChannelsFromApiIntoDbUseCase(repository)

    @Singleton
    @Provides
    fun provideFetchProgramsFromApiToDbUseCase(repository: TVRepository): FetchProgramsFromApiIntoDbUseCase =
        FetchProgramsFromApiIntoDbUseCase(repository)

    @Singleton
    @Provides
    fun provideFetchDaysFromApiToDbUseCase(repository: TVRepository): FetchDaysFromApiIntoDbUseCase =
        FetchDaysFromApiIntoDbUseCase(repository)

    @Singleton
    @Provides
    fun provideRemoveAllGroupsUseCase(repository: TVRepository): RemoveAllGroupsUseCase =
        RemoveAllGroupsUseCase(repository)

    @Singleton
    @Provides
    fun provideRemoveAllChannelsUseCase(repository: TVRepository): RemoveAllChannelsUseCase =
        RemoveAllChannelsUseCase(repository)

    @Singleton
    @Provides
    fun provideRemoveAllProgramsUseCase(repository: TVRepository): RemoveAllProgramsUseCase =
        RemoveAllProgramsUseCase(repository)

    @Singleton
    @Provides
    fun provideRemoveAllDaysUseCase(repository: TVRepository): RemoveAllDaysUseCase =
        RemoveAllDaysUseCase(repository)
}