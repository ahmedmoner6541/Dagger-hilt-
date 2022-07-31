package com.example.hilttutorial.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.hilttutorial.adapter.ProductAdapter
import com.example.hilttutorial.databinding.FragmentHomeBinding
import com.example.hilttutorial.util.visable
import com.kadirkuruca.newsapp.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val TAG = "HomeFragment"

    @Inject
    lateinit var viewModel: HomeViewModel
    lateinit var adapter: ProductAdapter

    private lateinit var binding: FragmentHomeBinding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        navController = findNavController()

        adapter = ProductAdapter()
        //    adapter.clickListner = this // always i forget it
        binding.rv.adapter = adapter


        //  automatic initialize with hilt
        // viewModel = ViewModelProvider(this, ViewModelFactory(HomeRepository())).get(HomeViewModel::class.java)




        viewModel.getAllProducts()
        viewModel.product.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    binding.progressBar.visable(false)
                    Log.d(TAG, "onViewCreated: ${it}")
                    adapter.setData(it.value.data.products)

                }
                is Resource.Loading -> {
                    binding.progressBar.visable(true)
                }
                is Resource.Failure -> {
                    Log.d(TAG, "onCreateView: ${it.errorResponse}")
                }
            }
        })

        return binding.root


    }
/*
    override fun onItemClick(productResponse: ProductResponse) {
        Log.d(TAG, "onItemClick: ${productResponse.data.products[0]}")
    }*/


}