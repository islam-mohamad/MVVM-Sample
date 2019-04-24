package com.sal3awy.thed.home.model.remote

import com.sal3awy.thed.utils.AppConstants
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ProductRemoteSource @Inject
constructor(private val service: ProductService) {

    fun getProducts() = service.getProducts(AppConstants.BASE_URL)
        .map { it.products }
        .subscribeOn(Schedulers.io())

}