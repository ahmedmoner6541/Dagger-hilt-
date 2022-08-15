package com.EcommerceApplication.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.EcommerceApplication.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint

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


