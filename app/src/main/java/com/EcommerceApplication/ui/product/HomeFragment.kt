package com.EcommerceApplication.ui.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.EcommerceApplication.R
import com.EcommerceApplication.adapter.ProductAdapter
import com.EcommerceApplication.data.models.Banner
import com.EcommerceApplication.data.models.Product
import com.EcommerceApplication.databinding.FragmentHomeBinding
import com.EcommerceApplication.util.Snackbar
import com.EcommerceApplication.util.handleApiError
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.kotlinproject.ui.base.BaseFragment
import com.google.android.material.navigation.NavigationView
import com.kadirkuruca.newsapp.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(),
    ProductAdapter.OnProductClick {
    private val TAG = "HomeFragment"

    @Inject
    lateinit var viewModel: ProductViewModel


    lateinit var adapter: ProductAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setupRecyclerView()
        setupObservers()

        binding.homeFabAddProduct.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_cartFragment)

        }

    }



    // TODO: get date CACHING
    private fun setupObservers() {
        viewModel.getAllProductApi()
        viewModel.productApi.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    hideProgress(binding.loaderLayout)
                    adapter.setData(it.value.data.products)

                    listOfBannerImages(it.value.data.banners)
                    CoroutineScope(Dispatchers.IO).launch {
                        viewModel.upsert(it.value.data.products)
                    }
                }
                is Resource.Loading -> {
                    showProgress(binding.loaderLayout)
                }
                is Resource.Failure -> handleApiError(it) { setupObservers()
                    showProgress(binding.loaderLayout)
                }
                /*   is Resource.Failure -> {

                       Log.d(TAG, "onCreateView: ${it.errorResponse}")
                       if (it.isNetworkError) {
                           getSavedAllProduct()
                       }
                   }*/
            }
        })

    }

    private fun listOfBannerImages(banners: List<Banner>) {
        val imageList = ArrayList<SlideModel>()
        banners.forEach {
            imageList.add(SlideModel(it.image.toString()))
        }

        binding.imageSliderHomeProducts.setImageList(imageList, ScaleTypes.FIT)
    }


    private fun setupRecyclerView() {
        adapter = ProductAdapter()
        adapter.onProductClick = this
        binding.rv.adapter = adapter
    }


    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentHomeBinding =
        FragmentHomeBinding.inflate(inflater, container, false)


    override fun onItemClick(product: Product) {
        val action =
            HomeFragmentDirections.actionHomeFragmentToDetailsProductFragment(product, null)
        findNavController().navigate(action)

    }


    override fun onFavClickClick(productId: String) {
        viewModel.setProductToFavoriteById(productId)
        viewModel.addFav.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {

                    if (it.value.message.equals(getString(R.string.Added))) {
                        requireView().Snackbar(getString(R.string.Added))
                    }
                    if (it.value.message.equals(getString(R.string.Deleted))) {
                        requireView().Snackbar(getString(R.string.Deleted))
                    }
                    hideProgress(binding.loaderLayout)

                }
                is Resource.Loading -> {
                    showProgress(binding.loaderLayout)
                }
                is Resource.Failure -> {
                    requireView().Snackbar("Error to Add To Favorite")
                    handleApiError(it)
                }


            }
        })
    }

}

