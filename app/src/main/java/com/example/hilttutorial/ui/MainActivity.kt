package com.example.hilttutorial.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.hilttutorial.R
import com.example.hilttutorial.adapter.MyAdapter
import com.example.hilttutorial.model.Model

class MainActivity : AppCompatActivity() {
    lateinit var rv: RecyclerView
    lateinit var adapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rv = findViewById(R.id.rv)
        adapter = MyAdapter()

        rv.adapter = adapter
        adapter.setData(myList())
    }


    fun myList(): ArrayList<Model> {
        /*var arrayList:ArrayList<Model>
        arrayList = arrayListOf()*/
        var arrayList: ArrayList<Model> = arrayListOf()
        arrayList.add(Model("ahmed", R.drawable.pokimon))
        arrayList.add(Model("moner", R.drawable.pokimon))
        arrayList.add(Model("abdou", R.drawable.pokimon))
        arrayList.add(Model("alhame", R.drawable.pokimon))
        return arrayList
    }
}

