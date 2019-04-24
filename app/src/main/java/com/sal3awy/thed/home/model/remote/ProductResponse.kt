package com.sal3awy.thed.home.model.remote

import com.google.gson.annotations.SerializedName
import com.sal3awy.thed.home.model.entity.Product

data class ProductResponse (@SerializedName("data") var products : List<Product>){
}