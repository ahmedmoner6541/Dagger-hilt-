package com.example.hilttutorial.repository

import android.util.Log
import com.example.hilttutorial.R

import com.example.hilttutorial.model.source.network.ApiProuct
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val api:ApiProuct
) : BaseRepositoty() {

    suspend fun getAllProduct()= safeApiCall {
        api.getAllProduct()
    }
}


/*    suspend fun getAllProduct(): Flow<List<ProductResponse>> = flow{
        val response = api.getAllProduct()
        Log.d(TAG, "getAllProduct: ${response}")
        emit(response)
       }.flowOn(Dispatchers.IO)
    }*/
/*
    fun myList(): ArrayList<Model> { //this method returns an arraylist
        var lst = ArrayList<Model>()
        var post1 = Model("ahmed", R.drawable.pokimon)
        var post2 = Model("moner", R.drawable.pokimon)
        var post3 = Model("abdou", R.drawable.pokimon)
        var post4 = Model("sara", R.drawable.pokimon)
        lst.add(post1)
        lst.add(post2)
        lst.add(post3)
        lst.add(post4)
        return lst
    }*/




