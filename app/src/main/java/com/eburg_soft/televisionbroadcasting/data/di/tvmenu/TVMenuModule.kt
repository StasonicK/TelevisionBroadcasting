package com.eburg_soft.televisionbroadcasting.data.di.tvmenu

import com.eburg_soft.televisionbroadcasting.data.di.scopes.PerScreen
import com.eburg_soft.televisionbroadcasting.presentation.main.TVMenuContract
import com.eburg_soft.televisionbroadcasting.presentation.main.TVMenuPresenter
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface TVMenuModule {

    @PerScreen
    @Binds
    fun provideTVMenuPresenter(presenter: TVMenuPresenter): TVMenuContract.Presenter
}