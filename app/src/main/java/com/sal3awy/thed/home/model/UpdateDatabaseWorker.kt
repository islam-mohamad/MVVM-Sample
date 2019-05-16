package com.sal3awy.thed.home.model

import android.content.Context
import androidx.annotation.NonNull
import androidx.work.Worker
import androidx.work.WorkerParameters
import dagger.android.support.AndroidSupportInjection
import io.reactivex.disposables.Disposable


class UpdateDatabaseWorker(@NonNull context: Context, @NonNull workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    init {
//        AndroidSupportInjection.inject(this)
    }
    var disposable: Disposable? = null

//    @Inject
//    lateinit var repo: ProductRepo

    @NonNull
    override fun doWork(): Result {

//        disposable = repo.getProducts().subscribe()
        return Result.success()
    }

    override fun onStopped() {
        disposable?.dispose()
        super.onStopped()
    }
}