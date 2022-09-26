package com.example.kotlinproject.ui.base

import androidx.lifecycle.ViewModel
import com.EcommerceApplication.base.BaseRepositoty
import com.EcommerceApplication.data.remote.network.UserApi


abstract class BaseViewModel(
    private val repositoty: BaseRepositoty,
) : ViewModel() {

    suspend fun logout(api: UserApi) = repositoty.logout(api)
}