package com.sal3awy.thed.home.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.sal3awy.thed.base.BaseViewModel
import com.sal3awy.thed.home.model.ProductRepo
import com.sal3awy.thed.home.model.entity.Product
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ProductViewModel(private val productRepo: ProductRepo) : BaseViewModel() {

    private val productsLiveData = MutableLiveData<List<Product>>()

    fun getProducts() {
        if (productsLiveData.value == null) {
            setIsLoading(true)
            compositeDisposable.add(
                productRepo.getProducts()
                    .subscribe({ products ->
                        productsLiveData.value = products
                        setIsLoading(false)
                    }, {
                        setIsLoading(false)
                        setErrorMessage(it!!.message)
                    })
            )

        }
    }

    fun getProductsLiveData(): MutableLiveData<List<Product>> {
        return this.productsLiveData
    }
}