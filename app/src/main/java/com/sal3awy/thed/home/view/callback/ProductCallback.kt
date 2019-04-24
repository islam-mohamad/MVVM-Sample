package com.sal3awy.thed.home.view.callback

import com.sal3awy.thed.binding.RecyclerViewCallback
import com.sal3awy.thed.home.model.entity.Product

interface ProductCallback : RecyclerViewCallback {
    fun productClicked(product: Product)
}