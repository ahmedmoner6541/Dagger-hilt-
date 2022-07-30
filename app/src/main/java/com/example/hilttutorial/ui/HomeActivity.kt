package com.example.hilttutorial.ui

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.hilttutorial.R
import com.example.hilttutorial.ViewModelFactory
import com.example.hilttutorial.adapter.MyAdapter
import com.example.hilttutorial.databinding.ActivityHomeBinding
import com.example.hilttutorial.repository.HomeRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private val TAG = "HomeActivity"
    private lateinit var binding:ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)




        
    }


}


