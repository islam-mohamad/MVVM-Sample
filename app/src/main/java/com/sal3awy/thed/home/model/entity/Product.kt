package com.sal3awy.thed.home.model.entity

import androidx.annotation.NonNull
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "product")
data class Product(
    @PrimaryKey
    @NonNull
    @SerializedName("id")
    var id: Int,
    @SerializedName("name")
    var name: String,
    @SerializedName("image")
    @Embedded
    var image: Image,
    @SerializedName("productDescription")
    var productDescription: String,
    @SerializedName("price")
    var price: Int
) : Serializable