package com.sal3awy.thed.dagger.modules

import androidx.lifecycle.ViewModelProviders
import com.sal3awy.thed.dagger.ActivityScoped
import com.sal3awy.thed.dagger.FragmentScoped
import com.sal3awy.thed.home.model.local.ProductDataBase
import com.sal3awy.thed.home.model.remote.ProductService
import com.sal3awy.thed.home.view.ui.HomeActivity
import com.sal3awy.thed.home.view.ui.HomeFragment
import com.sal3awy.thed.home.viewmodel.ProductViewModel
import com.sal3awy.thed.home.viewmodel.ProductViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import retrofit2.Retrofit


@Module
abstract class HomeModule {

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun homeFragment(): HomeFragment


    @Module
    companion object {

        @JvmStatic
        @ActivityScoped
        @Provides
        fun productsViewModel(homeActivity: HomeActivity, factory: ProductViewModelFactory) =
            ViewModelProviders.of(homeActivity, factory).get(ProductViewModel::class.java)

        @JvmStatic
        @ActivityScoped
        @Provides
        fun productService(retrofit: Retrofit) = retrofit.create(ProductService::class.java)!!

        @JvmStatic
        @ActivityScoped
        @Provides
        fun productDao(homeActivity: HomeActivity) = ProductDataBase.getDatabase(homeActivity).productDao()
    }


}