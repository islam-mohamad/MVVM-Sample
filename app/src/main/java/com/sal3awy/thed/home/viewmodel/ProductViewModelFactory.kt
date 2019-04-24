package com.sal3awy.thed.home.viewmodel

import androidx.annotation.NonNull
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sal3awy.thed.home.model.ProductRepo
import javax.inject.Inject

class ProductViewModelFactory @Inject
constructor(
    private val repo: ProductRepo
) : ViewModelProvider.NewInstanceFactory() {

    @NonNull
    override fun <T : ViewModel> create(@NonNull modelClass: Class<T>): T {

        @Suppress("UNCHECKED_CAST")
        return ProductViewModel(repo) as T
    }
}