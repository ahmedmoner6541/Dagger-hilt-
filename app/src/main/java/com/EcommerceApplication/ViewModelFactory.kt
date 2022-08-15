package com.EcommerceApplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.EcommerceApplication.data.remote.network.ApiProuct
import com.EcommerceApplication.repository.ProductRepository
import com.EcommerceApplication.ui.home.ProductViewModel

class ViewModelFactory(
    private val homeRepository: ProductRepository,
    private val apiProuct: ApiProuct
) :ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProductViewModel(homeRepository) as T
    }
}








