package com.example.kotlinproject.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.EcommerceApplication.base.BaseRepositoty
import com.EcommerceApplication.repository.AuthRepositoryImpl
import com.EcommerceApplication.repository.ProductRepository
import com.EcommerceApplication.ui.auth.AuthViewModel
import com.EcommerceApplication.ui.product.ProductViewModel


class ViewModelFactory(
    private val repository: BaseRepositoty
):ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

      return  when{
          // TODO: كل فيو موديل جديد هيتضاف هنا
            modelClass.isAssignableFrom(AuthViewModel::class.java) ->AuthViewModel(repository as AuthRepositoryImpl)as T
             modelClass.isAssignableFrom(ProductViewModel::class.java) -> ProductViewModel(repository as ProductRepository) as T
            else-> throw IllegalArgumentException("viewModelClass Not Found")

        }
    }
}