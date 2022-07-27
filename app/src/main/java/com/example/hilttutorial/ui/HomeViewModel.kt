package com.example.hilttutorial.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hilttutorial.model.Model
import com.example.hilttutorial.repository.HomeRepository

class HomeViewModel(
    private val homeRepository: HomeRepository
    ): ViewModel() {

    fun getlist(): ArrayList<Model> {
        return homeRepository.myList()

    }

}



