package com.sal3awy.thed

import android.content.Context
import androidx.work.*
import com.sal3awy.thed.dagger.DaggerAppComponent
import com.sal3awy.thed.home.model.UpdateDatabaseWorker
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import java.util.concurrent.TimeUnit


class App : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        scheduleWorkManager()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }

    companion object {
        fun get(activity: Context) = activity.applicationContext as App
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