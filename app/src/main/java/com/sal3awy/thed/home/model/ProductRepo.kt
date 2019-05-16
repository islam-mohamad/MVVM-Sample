package com.sal3awy.thed.home.model

import com.sal3awy.thed.home.model.local.ProductLocalSource
import com.sal3awy.thed.home.model.remote.ProductRemoteSource
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executor
import javax.inject.Inject

class ProductRepo @Inject
constructor(
    private val productRemoteSource: ProductRemoteSource,
    private val productLocalSource: ProductLocalSource,
    private val executor: Executor
) {

    fun getProducts() = Flowable.mergeDelayError(productLocalSource.getProducts()
        .observeOn(AndroidSchedulers.mainThread(), true)
        .subscribeOn(Schedulers.io()),
        productRemoteSource.getProducts()
            .observeOn(AndroidSchedulers.mainThread(), true)
            .subscribeOn(Schedulers.io())
            .doOnNext {
                executor.execute { productLocalSource.saveProducts(it) }
            }.onErrorReturn {
                ArrayList()
            }
    )
}