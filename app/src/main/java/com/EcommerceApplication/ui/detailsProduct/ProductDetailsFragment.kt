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
import com.EcommerceApplication.data.models.Product
import com.EcommerceApplication.data.remote.response.CartItem
import com.EcommerceApplication.data.remote.response.productDetailes.Data
import com.EcommerceApplication.databinding.FragmentProductDetailsBinding
import com.EcommerceApplication.ui.product.ProductViewModel
import com.EcommerceApplication.util.Snackbar
import com.EcommerceApplication.util.handleApiError
import com.EcommerceApplication.util.visable
import com.denzcoskun.imageslider.models.SlideModel
import com.example.kotlinproject.ui.base.BaseFragment
import com.google.android.material.appbar.AppBarLayout
import com.kadirkuruca.newsapp.util.Resource
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProductDetailsFragment : BaseFragment<FragmentProductDetailsBinding>() {

    @Inject
    lateinit var viewModel: ProductDetailsViewModel

    @Inject
    lateinit var productViewModel: ProductViewModel


    // TODO:  اي المعلومات اللي احنا محتاجينها
    var productFromHome: Product? = null
    var productInCart: CartItem? = null

    //private val args2: DetailsProductFragmentArgs by navArgs()
    private val args by navArgs<ProductDetailsFragmentArgs>()
    var totalquantity: Int = 1
    private val TAG = "DetailsProductFragment"

    var productId: Int? = null
    var name: String? = null
    var price: Double? = null
    var description: String? = null
    var dicsound: Int? = null
    var old_price: Double? = null

    var productIdInCart: Int = 0//هيتشحنو بعض الاضافه ولو تعديل هييجو في لارسبونس
    var quantity: Int = 0
    var totalPrice: Int = 0


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hideProgress(binding.loaderLayout)
        productFromHome = args.product
        productInCart = args.cartItemProduct

        if (productInCart == null) { // came from product fragment
            Log.d(TAG, "onViewCreated: came from product fragment")
            productId = productFromHome!!.id
            name = productFromHome!!.name
            price = productFromHome!!.price
            description = productFromHome!!.description
            dicsound = productFromHome!!.discount
            old_price = productFromHome!!.old_price

            productIdInCart = 0
            quantity = 0
            totalPrice = productFromHome!!.price.toInt() * totalquantity

            binding.addToCart.setOnClickListener {

                //ADD AND UPDATE

                addToCartObservers()
            }

            /*  الاي دي ده فيه مشكله هل هو بتاع المنتج ولا التعديل وايي الاسكوب بتاعهم   */
        }
        if (productFromHome == null) {// come from cart
            Log.d(TAG, "onViewCreated:   come from cart")
            productId = productInCart!!.product.id
            name = productInCart!!.product.name
            price = productInCart!!.product.price.toDouble()
            description = productInCart!!.product.description
            dicsound = productInCart!!.product.discount
            old_price = productInCart!!.product.old_price.toDouble()

            productIdInCart = productInCart!!.id
            quantity = productInCart!!.quantity
            totalPrice = (productInCart!!.product.price * totalquantity).toInt()  // reviison

            binding.tvQuantity.text = quantity.toString()

            binding.addToCart.setOnClickListener {
                updateProductQuantityInCart(productIdInCart, totalquantity)

            }
        }

        collapsingToolbar()
        productQuantity()
        detailsProductObservers()
        viewLocalData()


    }

    private fun setFavorite(productId: Int?) {
        productViewModel.setProductToFavoriteById(productId.toString())
        productViewModel.addFav.observe(viewLifecycleOwner, Observer {
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

    private fun detailsProductObservers() {
        viewModel.setIdForProductDetails(productId.toString())
        viewModel.productDetails.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {

                    binding.imageProduct.visibility = View.GONE
                    binding.imageSlider.visibility = View.VISIBLE
                    initviews(it.value.data)
                    Log.e(TAG, " in_favorites = ${it.value.data.in_favorites}")

                }
                is Resource.Loading -> {
                    binding.imageProduct.visable(true)
                    binding.imageSlider.visibility = View.GONE

                    Log.d(TAG, "detailsProductObservers: ")
                }
                is Resource.Failure -> {
                    Log.d(TAG, "onCreateView: ${it.errorResponse}")
                    if (it.isNetworkError) {
                        Log.d(TAG, "setupObservers: Resource.Failure")
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
                binding.tvTitleAppar.text = name
            }
            if (scrollRange + verticalOffset == 0) {
                binding.tvTitleAppar.visable(false)
                binding.collapsingToolbar.title = " السعر : ${price!! * totalquantity} "
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
        viewModel.addOrDeleteProductToCartById(productId.toString())
        viewModel.addOrDeleteProductToCartById.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    hideProgress(binding.loaderLayout)
                    Log.d(TAG, "addToCartObservers: it = ${it}")
                    var productIdInCart = it.value.data.id
                    updateProductQuantityInCart(productIdInCart, totalquantity)

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

    private fun updateProductQuantityInCart(productIdInCart: Int, totalquantity: Int) {

        viewModel.updateProductQuantityInCart(productIdInCart, totalquantity)
        viewModel.updateProductQuantityInCart.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    hideProgress(binding.loaderLayout)
                    findNavController().navigate(R.id.action_detailsProductFragment_to_cartFragment)
                    //     Log.d(TAG, "onItemClick: Success  test${it.value.data.cart}")
                    Log.d(TAG, "onItemClick: Success  test${it.value?.data?.cart?.quantity}")
                }
                is Resource.Loading -> {
                    Log.d(TAG, "onItemClick: Loading${it.message}")
                }
                is Resource.Failure -> {
                    Log.d(TAG, "onItemClick: Failure ${it.data}")
                }
            }
        })
    }


    private fun viewLocalData() {

        Log.d(TAG, "viewLocalData: ${productFromHome}")
        binding.nameProduct.text = name
        binding.descriptionProduct.text = description
        binding.tvDiscount.text = dicsound.toString()
        binding.pricrProduct.text = price.toString()
        binding.tvProductOldprice.text = old_price.toString()

        if (productInCart == null) {
            Log.d(TAG, "viewLocalData: dafd")
            Picasso.get().load(productFromHome!!.image).into(binding.imageProduct)
            val imageList = ArrayList<SlideModel>()
            productFromHome!!.images.forEach {
                imageList.add(SlideModel(it))
            }
        } else {
            Log.d(TAG, "viewLocalData: dafd22")
            Picasso.get().load(productInCart!!.product.image).into(binding.imageProduct)
            val imageList = ArrayList<SlideModel>()
            productInCart!!.product.images.forEach {
                imageList.add(SlideModel(it))
            }
        }


    }

    private fun initviews(product: Data) {
        binding.apply {
            // TODO: اخفاء كلا من نسبه الخصم والسعر القديم لو الخصم يساوي صفر
            nameProduct.text = product.name
            descriptionProduct.text = product.description
            tvDiscount.text = product.discount.toString()
            pricrProduct.text = product.price.toString()
            tvProductOldprice.text = product.old_price.toString()

            val imageList = ArrayList<SlideModel>()
            product.images.forEach {
                imageList.add(SlideModel(it.toString()))
            }
            imageSlider.setImageList(imageList)

            Log.d(TAG, "initviews: ${product.in_favorites}")
            if (product.in_favorites) {
                binding.chIsFavoriteInProdductDetails!!.isChecked = true
            }
            if (!product.in_favorites) {
                binding.chIsFavoriteInProdductDetails!!.isChecked = false
            }

            binding.chIsFavoriteInProdductDetails!!.setOnClickListener {
                if (binding.chIsFavoriteInProdductDetails!!.isChecked == true) { // is already fav remove it
                    setFavorite(productId)
                }
                if (binding.chIsFavoriteInProdductDetails!!.isChecked == false) {
                    setFavorite(productId)
                }


            }


        }
    }

    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentProductDetailsBinding =
        FragmentProductDetailsBinding.inflate(inflater, container, false)

}



