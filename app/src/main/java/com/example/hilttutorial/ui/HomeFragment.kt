package com.example.hilttutorial.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.ahmedmoner.intefaces.OnListnerItemClick
import com.example.hilttutorial.R
import com.example.hilttutorial.ViewModelFactory
import com.example.hilttutorial.adapter.MyAdapter
import com.example.hilttutorial.databinding.FragmentHomeBinding
import com.example.hilttutorial.model.Model
import com.example.hilttutorial.repository.HomeRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.math.log

@AndroidEntryPoint
class HomeFragment : Fragment() , OnListnerItemClick {
    private val TAG = "HomeFragment"

    @Inject
    lateinit var viewModel: HomeViewModel
    lateinit var adapter: MyAdapter

    private lateinit var binding: FragmentHomeBinding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        navController = findNavController()

        adapter = MyAdapter()
        adapter.clickListner = this // always i forget it 
        binding.rv.adapter = adapter
        

        binding.buttontest.setOnClickListener {
           navController.navigate(R.id.action_homeFragment_to_testFragment)
        }



        //  automatic initialize with hilt
     //   viewModel = ViewModelProvider(this, ViewModelFactory(HomeRepository())).get(HomeViewModel::class.java)
        binding.button.setOnClickListener {
            viewModel.getUser()
            viewModel.user.observe(viewLifecycleOwner, Observer {
                adapter.setData(it)
            })
        }

        return binding.root


    }

    override fun onItemClick(model: Model) {
        Log.d(TAG, "onItemClick: ${model.name}")
    }


}