package com.example.hilttutorial.repository

import androidx.lifecycle.LiveData
import com.example.hilttutorial.R
import com.example.hilttutorial.model.Model
import javax.inject.Inject

class HomeRepository @Inject constructor()  {



    fun myList(): ArrayList<Model> { //this method returns an arraylist
        var lst = ArrayList<Model>()
        var post1 = Model("ahmed", R.drawable.pokimon)
        var post2 = Model("ahmed", R.drawable.pokimon)
        var post3 = Model("ahmed", R.drawable.pokimon)
        var post4 = Model("ahmed", R.drawable.pokimon)
        lst.add(post1)
        lst.add(post2)
        lst.add(post3)
        lst.add(post4)
        return lst
    }



}


