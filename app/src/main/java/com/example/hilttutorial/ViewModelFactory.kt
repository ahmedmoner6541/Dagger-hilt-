package com.example.hilttutorial

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hilttutorial.repository.HomeRepository
import com.example.hilttutorial.ui.HomeActivity
import com.example.hilttutorial.ui.HomeViewModel

class ViewModelFactory(
    private val homeRepository: HomeRepository
) :ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(homeRepository) as T
    }
}



