package com.sal3awy.thed

import android.app.Application
import android.content.Context
import androidx.work.*
import com.sal3awy.thed.dagger.App.AppComponent
import com.sal3awy.thed.dagger.App.DaggerAppComponent
import com.sal3awy.thed.dagger.App.NetworkModule
import com.sal3awy.thed.home.model.UpdateDatabaseWorker
import java.util.concurrent.TimeUnit

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        component = DaggerAppComponent.builder()
            .networkModule(NetworkModule())
            .build()

        scheduleWorkManager()
    }

    companion object {
        private var component: AppComponent? = null
        fun get(activity: Context) = activity.applicationContext as App
        fun getComponent() = component!!
    }

    private fun scheduleWorkManager() {

        val constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()


        val periodicWorkRequest = PeriodicWorkRequest.Builder(UpdateDatabaseWorker::class.java, 1, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance()
            .enqueueUniquePeriodicWork(
                "update_database",
                ExistingPeriodicWorkPolicy.KEEP,
                periodicWorkRequest
            )

    }
}