package com.EcommerceApplication.ui.cart

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.EcommerceApplication.R
import com.EcommerceApplication.data.remote.response.toProductAdapterModel
import com.EcommerceApplication.databinding.FragmentCartBinding
import com.EcommerceApplication.databinding.LayoutCircularLoaderBinding
import com.EcommerceApplication.ui.product.ProductViewModel
import com.EcommerceApplication.ui.product.ProductModel
import com.EcommerceApplication.util.Snackbar
import com.EcommerceApplication.util.handleApiError
import com.example.kotlinproject.ui.base.BaseFragment
import com.kadirkuruca.newsapp.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CartFragment : BaseFragment<FragmentCartBinding>(),
    CartAdapter.OnProductClick {

    private val TAG = "CartFragment"

    @Inject
    lateinit var viewModel: ProductViewModel


    lateinit var adapter: CartAdapter
    private lateinit var itemTouchHelper: ItemTouchHelper


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setupRecyclerView()
        ObserversCart()
        setDeleteFromSwipeRecycler()
         binding.cartCheckOutBtn.setOnClickListener {
          findNavController().navigate(R.id.action_cartFragment_to_addressesFragment)

        }

    }

    private fun setDeleteFromSwipeRecycler() {
        val simpleCallback = object :
            ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT
            ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder,
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                when (direction) {
                    ItemTouchHelper.LEFT -> {
                        var productId = adapter.getProductAt(position).id.toString()

                        viewModel.addOrDeleteProductToCartById(productId)
                        viewModel.addOrDeleteProductToCartById.observe(viewLifecycleOwner, Observer {
                            when (it) {
                                is Resource.Success -> {
                                    var productIdInCart = it.value.data.id

                                    ObserversCart()
                                    hideProgress(binding.loaderLayout)
                                    Log.d(TAG, "onViewCreated:addToCart Success${productIdInCart}")
                                }
                                is Resource.Loading -> {
                                    showProgress(binding.loaderLayout)

                                    Log.d(TAG, "onViewCreated:addToCart Loading")
                                }
                                is Resource.Failure -> {
                                    showProgress(binding.loaderLayout)

                                    Log.d(TAG, "onViewCreated:addToCart Failure")
                                }
                            }
                        })

                    }

                }
                Log.d(TAG, "onSwiped: position${position}")
            }
        }
        itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(binding.rvCart)
    }


    private fun ObserversCart() {
        viewModel.cart()
        viewModel.cart.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    hideProgress(binding.loaderLayout)
                    adapter.setData(it.value.data.cart_items.toProductAdapterModel())
                }
                is Resource.Loading -> {
                    Log.d(TAG, "onViewCreated: Loading")
                    showProgress(binding.loaderLayout)
                }
                is Resource.Failure -> {
                    showProgress(binding.loaderLayout)
                }
            }

        })


    }


    private fun setupRecyclerView() {
        adapter = CartAdapter()
        adapter.onProductClick = this
        binding.rvCart.adapter = adapter
        adapter.notifyDataSetChanged()

    }


    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentCartBinding =
        FragmentCartBinding.inflate(inflater, container, false)

    override fun onItemClick(cartItem: ProductModel, productIdInCart: Int, quantity: Int) {

        val action = CartFragmentDirections.actionCartFragmentToDetailsProductFragment(cartItem, "cart")
        findNavController().navigate(action)
    }

    override fun upDateQuantityProductInCart(cartItem: ProductModel, totalQuantity: Int) {
        // TODO:   notifyItemRangeChanged(position, mData.size());
        lifecycleScope.launch {
            viewModel.updateProductQuantityInCart(cartItem.cartId, totalQuantity)
        }
        viewModel.updateProductQuantityInCart.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                  //  Log.d(TAG, "upDateQuantityProductInCart: Success${it.value.data.cart.quantity}")
                    ObserversCart()
                }
                is Resource.Loading -> {
                    Log.d(TAG, "upDateQuantityProductInCart: Loading")
                }
                is Resource.Failure -> {
                    Log.d(TAG, "upDateQuantityProductInCart: Failure")
                }
            }
        })
    }



    override fun addTofavoriteItemClick(cartItem: ProductModel) {
        viewModel.setProductToFavoriteById(cartItem.id.toString()) // product id
        viewModel.addFav.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    Log.d(TAG, "addTofavoriteItemClick: ${it.value.message}")
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

    override fun deleteItemInCartClick(
        cartItem: ProductModel,
        productIdInCart: Int,
        quantity: Int,
        itemBinding: LayoutCircularLoaderBinding,
    ) {
        viewModel.addOrDeleteProductToCartById(productIdInCart.toString())
        viewModel.addOrDeleteProductToCartById.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    var productIdInCart = it.value.data.id

                    ObserversCart()
                    hideProgress(binding.loaderLayout)
                    Log.d(TAG, "onViewCreated:addToCart Success${productIdInCart}")
                }
                is Resource.Loading -> {
                    showProgress(binding.loaderLayout)

                    Log.d(TAG, "onViewCreated:addToCart Loading")
                }
                is Resource.Failure -> {
                    showProgress(binding.loaderLayout)

                    Log.d(TAG, "onViewCreated:addToCart Failure")
                }
            }
        })
     }


}