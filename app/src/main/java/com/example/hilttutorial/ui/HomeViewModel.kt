package com.example.hilttutorial.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hilttutorial.model.Model
import com.example.hilttutorial.repository.HomeRepository
import kotlinx.coroutines.launch
import javax.inject.Inject


class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository
): ViewModel() {

    private val mutableLiveDatalist:MutableLiveData<List<Model>> = MutableLiveData()
    val user:LiveData<List<Model>> get() = mutableLiveDatalist

    fun getUser()= viewModelScope.launch {
        mutableLiveDatalist.postValue(homeRepository.myList())
    }

}



