package com.EcommerceApplication.ui.product

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.EcommerceApplication.R
import com.EcommerceApplication.data.models.Banner
import com.EcommerceApplication.data.models.toProductAdapterModel
import com.EcommerceApplication.databinding.FragmentHomeBinding
import com.EcommerceApplication.util.Snackbar
import com.EcommerceApplication.util.handleApiError
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.kotlinproject.ui.base.BaseFragment
import com.kadirkuruca.newsapp.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(), ProductAdapter.OnProductClick {
    private val TAG = "HomeFragment"

    @Inject
    lateinit var viewModel: ProductViewModel
    lateinit var adapter: ProductAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupObservers()
        getSavedAllProduct()

        binding.homeFabAddProduct.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_cartFragment)
        }


        binding.include.homeSearchEditText.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                findNavController().navigate(R.id.action_homeFragment_to_sserchFragment)
            }
        }
    }

    // TODO: get date CACHING
    //all product
    private fun setupObservers() {
        viewModel.getAllProductApi()
        viewModel.productApi.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    hideProgress(binding.loaderLayout)
                    adapter.setData(it.value.data.products.toProductAdapterModel())
                    listOfBannerImages(it.value.data.banners)
                    CoroutineScope(Dispatchers.IO).launch {
                        viewModel.upsert(it.value.data.products)
                        Log.d(TAG, "setupObservers: CashData----->${it.value.data}")
                    }
                }

                is Resource.Loading -> {
                    showProgress(binding.loaderLayout)
                }

                is Resource.Failure -> handleApiError(it) {
                    setupObservers()
                    showProgress(binding.loaderLayout)
                    Log . d (TAG, "onCreateViewlhkjn1: ${it.errorResponse}")

                }

                is Resource.Failure -> {

                    Log.d(TAG, "onCreateViewlhkjn2: ${it.errorResponse}")
                    if (it.isNetworkError) {
                    }
                }
            }
        })

    }

    private fun getSavedAllProduct() {
         viewModel.getSavedAllProduct().observe(viewLifecycleOwner, Observer {
             hideProgress(binding.loaderLayout)
             adapter.setData(it.toProductAdapterModel())

        })
    }

    private fun listOfBannerImages(banners: List<Banner>) {
        //ON CLICK GO TO CATEGORY
        // TODO: make kategory in nav drawer
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

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false)

    override fun onItemClick(product: ProductModel) {

        val action =
            HomeFragmentDirections.actionHomeFragmentToDetailsProductFragment(product, "home")
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

