package com.sal3awy.thed.dagger.workmanager

import android.content.Context
import com.sal3awy.thed.home.model.local.ProductDataBase
import com.sal3awy.thed.home.model.remote.ProductService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class WorkManagerModule(private val context: Context) {

    @WorkManagerScope
    @Provides
    fun context() = context

    @WorkManagerScope
    @Provides
    fun productService(retrofit: Retrofit) = retrofit.create(ProductService::class.java)!!

    @WorkManagerScope
    @Provides
    fun productDao(context: Context) = ProductDataBase.getDatabase(context).productDao()
}