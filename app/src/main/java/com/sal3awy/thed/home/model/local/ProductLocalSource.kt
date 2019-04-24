package com.sal3awy.thed.home.model.local

import com.sal3awy.thed.home.model.entity.Image
import com.sal3awy.thed.home.model.entity.Product
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import org.intellij.lang.annotations.Flow
import javax.inject.Inject

class ProductLocalSource @Inject
constructor(private val productDao: ProductDao) {

    fun getProducts() = productDao.getProducts()
        .subscribeOn(Schedulers.io())

    fun saveProducts(products: List<Product>) {
        productDao.saveProduct(products)
    }

}