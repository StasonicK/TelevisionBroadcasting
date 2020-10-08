package com.eburg_soft.televisionbroadcasting.data.di.application

import com.eburg_soft.televisionbroadcasting.data.repository.TVRepository
import com.eburg_soft.televisionbroadcasting.domain.usecases.GetAllDaysUseCase
import com.eburg_soft.televisionbroadcasting.domain.usecases.GetAllGroupsUseCase
import com.eburg_soft.televisionbroadcasting.domain.usecases.GetChannelsByGroupIdUseCase
import com.eburg_soft.televisionbroadcasting.domain.usecases.GetProgramsByChannelIdUseCase
import com.eburg_soft.televisionbroadcasting.domain.usecases.RemoveAllChannelsUseCase
import com.eburg_soft.televisionbroadcasting.domain.usecases.RemoveAllDaysUseCase
import com.eburg_soft.televisionbroadcasting.domain.usecases.RemoveAllGroupsUseCase
import com.eburg_soft.televisionbroadcasting.domain.usecases.RemoveAllProgramsUseCase
import com.eburg_soft.televisionbroadcasting.domain.usecases.SaveChannelsFromApiToDbUseCase
import com.eburg_soft.televisionbroadcasting.domain.usecases.SaveDaysFromApiToDbUseCase
import com.eburg_soft.televisionbroadcasting.domain.usecases.SaveGroupsFromApiToDbReturnChannelIdsUseCase
import com.eburg_soft.televisionbroadcasting.domain.usecases.SaveProgramsFromApiToDbUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UseCaseModule {

    @Singleton
    @Provides
    fun provideGetAllGroupsUseCase(repository: TVRepository): GetAllGroupsUseCase = GetAllGroupsUseCase(repository)

    @Singleton
    @Provides
    fun provideGetChannelsByGroupIdUseCase(repository: TVRepository): GetChannelsByGroupIdUseCase =
        GetChannelsByGroupIdUseCase(repository)

    @Singleton
    @Provides
    fun provideGetProgramsByChannelIdUseCase(repository: TVRepository): GetProgramsByChannelIdUseCase =
        GetProgramsByChannelIdUseCase(repository)


    @Singleton
    @Provides
    fun provideGetAllDaysUseCase(repository: TVRepository): GetAllDaysUseCase =
        GetAllDaysUseCase(repository)

    @Singleton
    @Provides
    fun provideSaveGroupsFromApiToDbReturnChannelIdsUseCase(repository: TVRepository): SaveGroupsFromApiToDbReturnChannelIdsUseCase =
        SaveGroupsFromApiToDbReturnChannelIdsUseCase(repository)

    @Singleton
    @Provides
    fun provideSaveChannelsFromApiToDbUseCase(repository: TVRepository): SaveChannelsFromApiToDbUseCase =
        SaveChannelsFromApiToDbUseCase(repository)

    @Singleton
    @Provides
    fun provideSaveProgramsFromApiToDbUseCase(repository: TVRepository): SaveProgramsFromApiToDbUseCase =
        SaveProgramsFromApiToDbUseCase(repository)

    @Singleton
    @Provides
    fun provideSaveDaysFromApiToDbUseCase(repository: TVRepository): SaveDaysFromApiToDbUseCase =
        SaveDaysFromApiToDbUseCase(repository)

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