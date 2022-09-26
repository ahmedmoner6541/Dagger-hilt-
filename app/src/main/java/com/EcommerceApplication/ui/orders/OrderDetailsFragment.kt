package com.EcommerceApplication.ui.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.EcommerceApplication.data.remote.response.order.details.Data
import com.EcommerceApplication.data.remote.response.order.details.Product
import com.EcommerceApplication.data.remote.response.order.details.toProductAdapterModel
import com.EcommerceApplication.databinding.FragmentOrderDetailsBinding
import com.EcommerceApplication.util.handleApiError
import com.example.kotlinproject.ui.base.BaseFragment
import com.kadirkuruca.newsapp.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OrderDetailsFragment : BaseFragment<FragmentOrderDetailsBinding>() {
    private val TAG = "OrderDetailsFragment"

    @Inject
    lateinit var viewModel: OrderViewModel

    lateinit var adapter: ProductInOrderAdapter

    private val args by navArgs<OrderDetailsFragmentArgs>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var orderId = args.orderId
        //       findNavController().navigateUp()

        //setObservers(orderId)
        setupRecyclerView()

        viewModel.orderDetalils(orderId)
        viewModel.orderDetails.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    hideProgress(binding.loaderLayout)

                    detailsOrder(it.value.data)
                }

                is Resource.Loading -> {
                    showProgress(binding.loaderLayout)
                }

                is Resource.Failure -> handleApiError(it) {
                    showProgress(binding.loaderLayout)

                }

            }
        })


    }

    private fun detailsOrder(data: Data) {
        setOrderDetailsViews(data)
        setOrderPaymentViews(data)
        setOrderProductsInRecycler(data.products)

    }

    private fun setOrderProductsInRecycler(products: List<Product>) {

        adapter.setDate(products.toProductAdapterModel())
    }


    private fun setOrderPaymentViews(data: Data) {
        binding.orderDetailsPaymentLayout.priceItemsAmountTv.text = data.cost.toString()
        binding.orderDetailsPaymentLayout.priceShippingAmountTv.text = "Shipping is free"
        binding.orderDetailsPaymentLayout.priceVatAmountTv.text = data.vat.toInt().toString()
        binding.orderDetailsPaymentLayout.priceDiscoundAmountTv.text = data.discount.toString()
        binding.orderDetailsPaymentLayout.pricePayMethodAmountTv.text = data.payment_method.toString()
     }

    private fun setOrderDetailsViews(data: Data) {

        binding.orderDetailsShippingAddLayout.shipDateValueTv.text = data.date
        binding.orderDetailsShippingAddLayout.shipAddValueTv.text = data.address.region+","+data.address.city
        binding.orderDetailsShippingAddLayout.nameValueTv.text = data.address.name.toString()
        binding.orderDetailsShippingAddLayout.phoneValueTv.text = data.address.notes.toString()
        binding.orderDetailsShippingAddLayout.shipCurrStatusValueTv.text = data.status
        binding.orderDetailsShippingAddLayout.shipCurrStatusValueTv.text = data.status
    }



    private fun setupRecyclerView() {
        adapter = ProductInOrderAdapter()
         binding.orderDetailsProRecyclerView.adapter = adapter
        adapter.notifyDataSetChanged()

    }

    /*
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
        }*/

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentOrderDetailsBinding =
        FragmentOrderDetailsBinding.inflate(inflater, container, false)


}