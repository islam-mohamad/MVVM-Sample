package com.sal3awy.thed.dagger

import com.sal3awy.thed.dagger.modules.HomeModule
import com.sal3awy.thed.home.view.ui.HomeActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = [HomeModule::class])
    abstract fun bindHomeActivity(): HomeActivity
}