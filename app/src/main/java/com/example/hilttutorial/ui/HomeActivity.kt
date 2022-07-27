package com.example.hilttutorial.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.hilttutorial.R
import com.example.hilttutorial.adapter.MyAdapter
import com.example.hilttutorial.presenter.HomePresenter


class HomeActivity : AppCompatActivity() ,Contract.Iview{
    private val TAG = "HomeActivity"
    lateinit var rv: RecyclerView
    lateinit var adapter: MyAdapter

    lateinit var  presenter:Contract.Ipresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        rv = findViewById(R.id.rv)
        presenter = HomePresenter(this)

        adapter = MyAdapter()
        rv.adapter = adapter
        adapter.setData(presenter.getList())



    }


    override fun onSuccess(result: String) {
        Log.d(TAG, "onSuccess:${result} ")
    }

    override fun onfailure(result: String) {
        Log.d(TAG, "onSuccess:${result} ")
    }


}

