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
import com.ahmedmoner.intefaces.OnListnerItemClick
import com.example.hilttutorial.adapter.ProductAdapter
import com.example.hilttutorial.databinding.FragmentHomeBinding
import com.example.hilttutorial.util.visable
import com.example.kotlinproject.data.model.responses.ProductResponse.Product
import com.example.kotlinproject.data.model.responses.ProductResponse.ProductResponse
import com.kadirkuruca.newsapp.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(), OnListnerItemClick {
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

        setupRecyclerView()
        setupObservers()

        return binding.root


    }

    private fun setupObservers() {
        viewModel.getAllProductApi()
        viewModel.productApi.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    binding.progressBar.visable(false)
                    adapter.setData(it.value.data.products)
                    CoroutineScope(Dispatchers.IO).launch {
                        viewModel.upsert(it.value.data.products)
                    }
                }
                is Resource.Loading -> {
                    binding.progressBar.visable(true)

                }
                is Resource.Failure -> {
                    Log.d(TAG, "onCreateView: ${it.errorResponse}")
                    if (it.isNetworkError){
                        getSavedAllProduct()
                    }
                }
            }
        })

    }

    private fun getSavedAllProduct() {
        viewModel.getSavedAllProduct().observe(viewLifecycleOwner, Observer {
           if (it != null) {
               adapter.setData(it)
               binding.progressBar.visable(false)
               Log.d(TAG, "test:local data  ")
           }
        })

    }

    override fun onItemClick(model: ProductResponse) {

    }

    private fun setupRecyclerView() {
        adapter = ProductAdapter()
        adapter.clickListner = this // always i forget it
        binding.rv.adapter = adapter
    }
}