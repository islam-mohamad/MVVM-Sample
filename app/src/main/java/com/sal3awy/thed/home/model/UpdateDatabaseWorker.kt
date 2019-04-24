package com.sal3awy.thed.home.model

import android.content.Context
import android.util.Log
import androidx.annotation.NonNull
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.sal3awy.thed.App
import com.sal3awy.thed.dagger.workmanager.DaggerWorkManagerComponent
import com.sal3awy.thed.dagger.workmanager.WorkManagerComponent
import com.sal3awy.thed.dagger.workmanager.WorkManagerModule
import com.sal3awy.thed.home.model.entity.Product
import io.reactivex.disposables.Disposable
import javax.inject.Inject


class UpdateDatabaseWorker(@NonNull context: Context, @NonNull workerParams: WorkerParameters) :
    Worker(context, workerParams) {
    var component: WorkManagerComponent? = null
    var disposable: Disposable? = null

    @Inject
    lateinit var repo: ProductRepo

    init {
        component = DaggerWorkManagerComponent.builder()
            .appComponent(App.getComponent())
            .workManagerModule(WorkManagerModule(context))
            .build()

        component?.injectUpdateDatabaseWorker(this)
    }

    @NonNull
    override fun doWork(): Result {

        disposable = repo.getProducts().subscribe()
        return Result.success()
    }

    override fun onStopped() {
        disposable?.dispose()
        super.onStopped()
    }
}