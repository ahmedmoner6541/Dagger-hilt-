package com.EcommerceApplication.data.local.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.EcommerceApplication.data.models.Product

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(product: List<Product>)

    @Query("SELECT * FROM Product")
    fun getSavedAllProduct(): LiveData<List<Product>>


}