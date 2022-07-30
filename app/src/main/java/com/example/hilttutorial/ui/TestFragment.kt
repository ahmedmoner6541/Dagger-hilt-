package com.example.hilttutorial.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.hilttutorial.R
import com.example.hilttutorial.databinding.FragmentTestBinding

class TestFragment : Fragment() {
private lateinit var binding: FragmentTestBinding
private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentTestBinding.inflate(inflater,container,false)
        navController = findNavController()

       binding.btnBackToHome.setOnClickListener {
           navController.navigate(R.id.action_testFragment_to_homeFragment)
       }

        return binding.root
    }


}