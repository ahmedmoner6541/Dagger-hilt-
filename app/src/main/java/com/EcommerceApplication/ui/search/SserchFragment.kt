package com.EcommerceApplication.ui.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.EcommerceApplication.data.remote.response.searchProduct.toProductAdapterModel
import com.EcommerceApplication.databinding.FragmentSserchBinding
import com.EcommerceApplication.ui.product.ProductViewModel
import com.EcommerceApplication.ui.product.ProductModel
import com.example.kotlinproject.ui.base.BaseFragment
import com.kadirkuruca.newsapp.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class SserchFragment : BaseFragment<FragmentSserchBinding>(),
        SearchAdapter.OnProductClick{
    private val TAG = "SserchFragment"

    @Inject
    lateinit var viewModel: ProductViewModel
    lateinit var adapter: SearchAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        showKeyboard(binding.include.homeSearchEditText)
        binding.include.homeSearchEditText.addTextChangedListener {
            setupObservers(it.toString())
        }
    }



    private fun setupObservers(productName: String) {
        viewModel.getSearchProductName(productName)
        viewModel.searchProductnameApi.observe(viewLifecycleOwner, Observer {
            when (it) {

                is Resource.Success -> {
                   hideProgress(binding.loaderLayout)

                    adapter.setData(it.value.data.data.toProductAdapterModel())
                    Log.d(TAG, "setupObservers: success${it.value.data.data}")
                }

                is Resource.Loading -> {
                    Log.d(TAG, "setupObservers: Loading")
                    showProgress(binding.loaderLayout)

                }

                is Resource.Failure -> {
                    Log.d(TAG, "setupObservers: Failure")
                    showProgress(binding.loaderLayout)

                }

            }

        })
    }

    private fun setupRecyclerView() {
        adapter = SearchAdapter()
        adapter.onProductClick = this
        binding.rvSearch.adapter = adapter
    }


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSserchBinding = FragmentSserchBinding.inflate(inflater,container,false)

    override fun onItemClick(product: ProductModel) {
         val action = SserchFragmentDirections.actionSserchFragmentToDetailsProductFragment(product, "search")
        Log.d(TAG, "onItemClick: ok")
        findNavController().navigate(action)


    }
}