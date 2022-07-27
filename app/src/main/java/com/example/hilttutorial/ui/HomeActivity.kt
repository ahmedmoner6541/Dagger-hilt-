package com.example.hilttutorial.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.hilttutorial.R
import com.example.hilttutorial.ViewModelFactory
import com.example.hilttutorial.adapter.MyAdapter
import com.example.hilttutorial.repository.HomeRepository


class HomeActivity : AppCompatActivity() {
    private val TAG = "HomeActivity"
    lateinit var rv: RecyclerView
    lateinit var adapter: MyAdapter

    private lateinit var viewModel: HomeViewModel
    private lateinit var viewModelFactory: ViewModelFactory


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        rv = findViewById(R.id.rv)

         viewModel =
            ViewModelProvider(this, ViewModelFactory(HomeRepository())).get(
                HomeViewModel::class.java
            )

        adapter = MyAdapter()
        rv.adapter = adapter
        adapter.setData(viewModel.getlist())


    }


}


