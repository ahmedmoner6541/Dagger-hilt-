package com.example.hilttutorial.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.hilttutorial.R
import com.example.hilttutorial.ViewModelFactory
import com.example.hilttutorial.adapter.MyAdapter
import com.example.hilttutorial.repository.HomeRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private val TAG = "HomeActivity"
    lateinit var rv: RecyclerView
    lateinit var adapter: MyAdapter

    @Inject
     lateinit var viewModel: HomeViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        rv = findViewById(R.id.rv)
        adapter = MyAdapter()
        rv.adapter = adapter

        //viewModel = ViewModelProvider(this, ViewModelFactory(HomeRepository())).get(HomeViewModel::class.java)

        viewModel.getUser()
        viewModel.user.observe(this, Observer {
            adapter.setData(it)
        })

//


    }


}


