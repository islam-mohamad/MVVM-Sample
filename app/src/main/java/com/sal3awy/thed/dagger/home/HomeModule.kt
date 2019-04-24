package com.sal3awy.thed.dagger.home

import androidx.lifecycle.ViewModelProviders
import com.sal3awy.thed.home.model.local.ProductDataBase
import com.sal3awy.thed.home.model.remote.ProductService
import com.sal3awy.thed.home.view.ui.HomeActivity
import com.sal3awy.thed.home.viewmodel.ProductViewModel
import com.sal3awy.thed.home.viewmodel.ProductViewModelFactory
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit


@Module
class HomeModule(private val homeActivity: HomeActivity) {

    @HomeScope
    @Provides
    fun productsViewModel(homeActivity: HomeActivity, factory: ProductViewModelFactory) =
        ViewModelProviders.of(homeActivity, factory).get(ProductViewModel::class.java)


    @HomeScope
    @Provides
    fun homeActivity() = homeActivity

    @HomeScope
    @Provides
    fun productService(retrofit: Retrofit) = retrofit.create(ProductService::class.java)

    @HomeScope
    @Provides
    fun productDao(homeActivity: HomeActivity) = ProductDataBase.getDatabase(homeActivity).productDao()
}