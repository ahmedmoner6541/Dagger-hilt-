package com.EcommerceApplication.ui.product

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.EcommerceApplication.R
import com.EcommerceApplication.adapter.ProductAdapter
import com.EcommerceApplication.data.models.Product
import com.EcommerceApplication.databinding.FragmentHomeBinding
import com.EcommerceApplication.util.visable
import com.example.kotlinproject.ui.base.BaseFragment

import com.kadirkuruca.newsapp.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ProductFragment : BaseFragment<FragmentHomeBinding>(),
        ProductAdapter.OnProductClick
{
    private val TAG = "HomeFragment"

    @Inject
    lateinit var viewModel: ProductViewModel
    lateinit var adapter: ProductAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupObservers()

        binding.button.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_cartFragment)

        }

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
               Log.d(TAG, "getSavedAllProduct: ")
           }
        })

    }

  

    private fun setupRecyclerView() {
        adapter = ProductAdapter()
        adapter.onProductClick = this
        binding.rv.adapter = adapter
    }



    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentHomeBinding
            = FragmentHomeBinding.inflate(inflater,container,false)

    override fun onItemClick(product: Product) {
            val action =
                ProductFragmentDirections.actionHomeFragmentToDetailsProductFragment(product,null)
            findNavController().navigate(action)

    }


}