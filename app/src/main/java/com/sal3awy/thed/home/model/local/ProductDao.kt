package com.sal3awy.thed.home.model.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.sal3awy.thed.home.model.entity.Image
import com.sal3awy.thed.home.model.entity.Product
import io.reactivex.Flowable

@Dao
interface ProductDao {

    @Insert(onConflict = REPLACE)
    fun saveProduct(products: List<Product>)

    @Query("SELECT * FROM product")
    fun getProducts(): Flowable<List<Product>>
}