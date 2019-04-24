package com.sal3awy.thed.dagger.workmanager

import com.sal3awy.thed.dagger.App.AppComponent
import com.sal3awy.thed.home.model.UpdateDatabaseWorker
import dagger.Component


@WorkManagerScope
@Component(modules = [WorkManagerModule::class], dependencies = [AppComponent::class])
interface WorkManagerComponent {
    fun injectUpdateDatabaseWorker(updateDatabaseWorker: UpdateDatabaseWorker)
}