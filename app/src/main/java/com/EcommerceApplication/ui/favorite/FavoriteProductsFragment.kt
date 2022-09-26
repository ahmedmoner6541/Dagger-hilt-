package com.EcommerceApplication.ui.favorite

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.EcommerceApplication.adapter.FavoriteProductAdapter
import com.EcommerceApplication.data.remote.response.favorite.getFavorites.DataX
import com.EcommerceApplication.data.remote.response.favorite.getFavorites.Product
import com.EcommerceApplication.databinding.FragmentFavoriteProductsBinding
import com.EcommerceApplication.ui.product.ProductViewModel
import com.EcommerceApplication.util.Snackbar
import com.example.kotlinproject.ui.base.BaseFragment
import com.kadirkuruca.newsapp.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteProductsFragment
    : BaseFragment<FragmentFavoriteProductsBinding>(),
    FavoriteProductAdapter.OnProductClick {

    @Inject
    lateinit var viewModel: ProductViewModel

    lateinit var adapter: FavoriteProductAdapter

    private val TAG = "FavoriteProductsFragmen"
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // TODO:  كانك بتجيب لابرودكتس عادي ولكن اللي ال in fav = true
        setupRecyclerView()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.geFavorite()
        viewModel.favoriteApi.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    adapter.setData(it.value.data.data)
                    hideProgress(binding.loaderLayout)

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
        adapter = FavoriteProductAdapter()
        adapter.onProductClick = this
        binding.rvFavorite.adapter = adapter
    }

    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentFavoriteProductsBinding =
        FragmentFavoriteProductsBinding.inflate(inflater, container, false)

    override fun onItemClick(product: Product) {

    }

    override fun onFavClickClick(productId: String) {


    }

}