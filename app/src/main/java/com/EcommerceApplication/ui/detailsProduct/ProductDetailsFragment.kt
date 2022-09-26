package com.EcommerceApplication.ui.detailsProduct

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.EcommerceApplication.R
import com.EcommerceApplication.data.remote.response.productDetailes.Data
import com.EcommerceApplication.databinding.FragmentProductDetailsBinding
import com.EcommerceApplication.ui.product.ProductViewModel
import com.EcommerceApplication.ui.product.ProductModel
import com.EcommerceApplication.util.Snackbar
import com.EcommerceApplication.util.handleApiError
import com.EcommerceApplication.util.visable
import com.denzcoskun.imageslider.models.SlideModel
import com.example.kotlinproject.ui.base.BaseFragment
import com.google.android.material.appbar.AppBarLayout
import com.kadirkuruca.newsapp.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProductDetailsFragment : BaseFragment<FragmentProductDetailsBinding>() {
    private val TAG = "ProductDetailsFragment"

    @Inject
    lateinit var viewModel: ProductDetailsViewModel

    @Inject
    lateinit var productViewModel: ProductViewModel

    // TODO:  اي المعلومات اللي احنا محتاجينها
    var product: ProductModel? = null
    var productComeFrom: String? = null

    var totalquantity: Int = 1
    var totalPrice: Int = 0

    private val args by navArgs<ProductDetailsFragmentArgs>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hideProgress(binding.loaderLayout)

        product = args.product
        productComeFrom = args.cartItemProduct.toString()




        Log.d(TAG, "onViewCreated: ${productComeFrom}")
        Log.d(TAG, "onViewCreated: product = ${product}")


        // TODO: you should make total quantity and price puplic for all this is best practice
        Log.d(TAG, "onViewCreated: quantity = ${product?.quantity}")
        if (productComeFrom == "search") {
            totalPrice = product!!.price.toInt() * totalquantity

            binding.addToCart.setOnClickListener {

              //  if (productInCart or not )
                addToCartObservers()
            }
        }
        if (productComeFrom == "home") { // came from product fragment
            binding.addToCart.setOnClickListener {
                //ADD AND UPDATE
                totalPrice = product!!.price.toInt() * totalquantity
                addToCartObservers()
            }

        }
        if (productComeFrom == "cart") {// come from cart
            totalquantity = product!!.quantity
            totalPrice = product!!.price.toInt() * totalquantity
            binding.tvQuantity.text = totalquantity.toString()
            binding.addToCart.setOnClickListener {
                updateProductQuantityInCart(product!!.cartId, totalquantity)
            }
        }
        collapsingToolbar()
        productQuantity()
        detailsProductObservers()

    }

    private fun setFavorite(productId: Int?) {
        productViewModel.setProductToFavoriteById(productId.toString())
        productViewModel.addFav.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    Log.d(TAG, "setFavorite: ${it.value.message}")
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
                    hideProgress(binding.loaderLayout)

                }


            }
        })
    }

    private fun detailsProductObservers() {
        viewModel.setIdForProductDetails(product?.id.toString())
        viewModel.productDetails.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    Log.d(TAG, "detailsProductObservers: success${it.value.data}")
                    binding.imageProduct.visibility = View.GONE
                    binding.imageSlider.visibility = View.VISIBLE
                    initviews(it.value.data)
                    hideProgress(binding.loaderLayout)

                }

                is Resource.Loading -> {
                    binding.imageProduct.visable(true)
                    binding.imageSlider.visibility = View.GONE
                    Log.d(TAG, "detailsProductObservers: load")
                    showProgress(binding.loaderLayout)
                }

                is Resource.Failure -> {
                    Log.d(TAG, "onCreateView: ${it.errorResponse}")
                    if (it.isNetworkError) {
                        Log.d(TAG, "setupObservers: Resource.Failure")
                        detailsProductObservers()
                    }
                }
            }
        })
    }


    private fun collapsingToolbar() {

        var isShow = true
        var scrollRange = -1
        binding.appbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { barLayout, verticalOffset ->
            if (scrollRange == -1) {
                scrollRange = barLayout?.totalScrollRange!!
                Log.d(TAG, "collapsingToolbar: 1")

                binding.tvTitleAppar.visable(true)
                binding.tvTitleAppar.text = product!!.name.toString()
            }
            if (scrollRange + verticalOffset == 0) {
                binding.tvTitleAppar.visable(false)
                binding.collapsingToolbar.title = " السعر : ${product?.price!! * totalquantity} "
                isShow = true
                Log.d(TAG, "collapsingToolbar: 2")

            } else if (isShow) {
                binding.collapsingToolbar.title =
                    " " //careful there should a space between double quote otherwise it wont work
                binding.tvTitleAppar.visable(true)
                /*  isShow = false image slider is hidden*/


            }
        })


    }

    private fun productQuantity() {// لو جاي من ال card  القيمه متحدده قبل كده
        binding.removeItem.setOnClickListener(View.OnClickListener {
            if (totalquantity < 10) {
                totalquantity++
                binding.tvQuantity.text = totalquantity.toString()
            }
        })
        binding.addItem.setOnClickListener(View.OnClickListener {
            if (totalquantity > 0) {
                totalquantity--
                binding.tvQuantity.text = totalquantity.toString()
            }
        })
    }

    fun addToCartObservers() {
        viewModel.addOrDeleteProductToCartById(product?.id.toString())
        viewModel.addOrDeleteProductToCartById.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    hideProgress(binding.loaderLayout)
                    Log.d(TAG, "addToCartObservers: it = ${it}")

                       updateProductQuantityInCart(it.value.data.id, totalquantity)

                }

                is Resource.Loading -> {
                    showProgress(binding.loaderLayout)
                    Log.d(TAG, "onViewCreated:addToCart Loading")
                }

                is Resource.Failure -> {
                    hideProgress(binding.loaderLayout)
                    handleApiError(it) { addToCartObservers() }
                    Log.d(TAG, "onViewCreated:addToCart Failure")
                }
            }
        })
    }

    private fun updateProductQuantityInCart(id: Int, totalquantity: Int) {
        viewModel.updateProductQuantityInCart(id, totalquantity)
        viewModel.updateProductQuantityInCart.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    hideProgress(binding.loaderLayout)
                    findNavController().navigate(R.id.action_detailsProductFragment_to_cartFragment)
                    //     Log.d(TAG, "onItemClick: Success  test${it.value.data.cart}")
                    Log.d(TAG, "onItemClick: Success  test${it.value}")
                }

                is Resource.Loading -> {
                    showProgress(binding.loaderLayout)
                    Log.d(TAG, "onItemClick: Loading${it.message}")
                }

                is Resource.Failure -> {
                    showProgress(binding.loaderLayout)
                    Log.d(TAG, "onItemClick: Failure ${it.data}")
                }
            }
        })
    }


    private fun initviews(product: Data) {
        binding.apply {
            // TODO: اخفاء كلا من نسبه الخصم والسعر القديم لو الخصم يساوي صفر
            nameProduct.text = product?.name
            descriptionProduct.text = product?.description
            tvDiscount.text = product?.discount.toString()
            pricrProduct.text = product?.price.toString()
            tvProductOldprice.text = product?.old_price.toString()


            val imageList = ArrayList<SlideModel>()
            product?.images?.forEach {
                imageList.add(SlideModel(it.toString()))
            }
            imageSlider.setImageList(imageList)

            if (product?.in_favorites == true) {
                binding.chIsFavoriteInProdductDetails!!.isChecked = true
            }
            if (product?.in_favorites == false) {
                binding.chIsFavoriteInProdductDetails!!.isChecked = false
            }

            binding.chIsFavoriteInProdductDetails!!.setOnClickListener {
                if (binding.chIsFavoriteInProdductDetails!!.isChecked == true) { // is already fav remove it
                    setFavorite(product?.id)
                }
                if (binding.chIsFavoriteInProdductDetails!!.isChecked == false) {
                    setFavorite(product?.id)
                }
            }
            if (product.discount == 0) {
                binding.tvProductOldprice.visibility = View.GONE
                binding.tvDiscount.visibility = View.GONE
                binding.tvPercentage.visibility = View.GONE
            }


        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentProductDetailsBinding =
        FragmentProductDetailsBinding.inflate(inflater, container, false)

}



