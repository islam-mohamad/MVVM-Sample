package com.sal3awy.thed.dagger.home

import com.sal3awy.thed.dagger.App.AppComponent
import com.sal3awy.thed.home.view.ui.HomeActivity
import dagger.Component

@HomeScope
@Component(modules = [HomeModule::class], dependencies = [AppComponent::class])
interface HomeComponent {
    fun injectHomeActivity(homeActivity: HomeActivity)
}