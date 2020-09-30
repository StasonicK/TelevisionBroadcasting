package com.eburg_soft.televisionbroadcasting.data.di.tvmenu

import com.eburg_soft.televisionbroadcasting.data.di.scopes.PerScreen
import com.eburg_soft.televisionbroadcasting.presentation.main.TVMenuFragment
import dagger.Subcomponent

@PerScreen
@Subcomponent(modules = [TVMenuModule::class])
interface TVMenuComponent {

    fun inject(fragment: TVMenuFragment)
}
