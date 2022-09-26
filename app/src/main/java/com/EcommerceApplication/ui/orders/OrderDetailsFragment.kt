package com.EcommerceApplication.ui.orders

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.EcommerceApplication.adapter.ProductAdapter
import com.EcommerceApplication.data.models.Product
import com.EcommerceApplication.databinding.FragmentOrdersBinding
import com.example.kotlinproject.ui.base.BaseFragment
import com.kadirkuruca.newsapp.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OrderDetailsFragment : BaseFragment<FragmentOrdersBinding>() {
    private val TAG = "OrderDetailsFragment"

    @Inject
    lateinit var viewModel: OrderViewModel

    lateinit var adapter: ProductAdapter

    private val args by navArgs<OrderDetailsFragmentArgs>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var orderId = args.orderId
        findNavController().navigateUp()

        setObservers(orderId)
        setViews()

        viewModel.orderDetalils(orderId)
        viewModel.orderDetails.observe(viewLifecycleOwner, Observer {

        })

        //-1 hidden + and - cont product


    }

    private fun setViews() {

    }

    private fun setObservers(orderId: String) {
        viewModel.orderDetalils(orderId)
        viewModel.orderDetails.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    Log.d(TAG, "setObservers: Success")

                    var productList: List<Product> = arrayListOf()

                    productList = it.value.data.products

                    adapter.setData(it.value.data.products)
                }
                is Resource.Loading -> {
                    Log.d(TAG, "setObservers: Loading")
                }
                is Resource.Failure -> {
                    Log.d(TAG, "setObservers: Failure")
                }
            }
        })
    }

    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentOrdersBinding =
        FragmentOrdersBinding.inflate(inflater, container, false)


}