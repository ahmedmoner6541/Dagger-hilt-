package com.example.hilttutorial.repository

import com.example.hilttutorial.model.source.network.ApiProuct
import com.example.kotlinproject.data.model.responses.ProductResponse.Product
import com.example.roomwithhilt.db.ProductDao
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val productDao: ProductDao,
    private val api: ApiProuct,
) : BaseRepositoty() {

    suspend fun getAllProductApi() = safeApiCall {
        api.getAllProduct()
    }


    fun upsert(product: List<Product>) = productDao.upsert(product)

    fun getSavedAllProduct() = productDao.getSavedAllProduct()


}



