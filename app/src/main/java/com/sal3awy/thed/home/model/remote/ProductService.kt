package com.sal3awy.thed.home.model.remote

import com.sal3awy.thed.home.model.entity.Product
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Url

interface ProductService {
    @GET
    fun getProducts(@Url url: String): Flowable<ProductResponse>
}