package com.eburg_soft.televisionbroadcasting.data.di.tvmenu

import com.eburg_soft.televisionbroadcasting.data.di.base.scopes.PerScreen
import com.eburg_soft.televisionbroadcasting.presentation.TVMenuActivity
import com.eburg_soft.televisionbroadcasting.presentation.main.TVMenuFragment
import dagger.Subcomponent

@PerScreen
@Subcomponent(modules = [TVMenuModule::class, TVMenuContextModule::class])
interface TVMenuComponent {

    fun inject(fragment: TVMenuActivity)
}
