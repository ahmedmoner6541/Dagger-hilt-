package com.EcommerceApplication.ui.orders

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.EcommerceApplication.R
import com.EcommerceApplication.adapter.ProductAdapter
import com.EcommerceApplication.databinding.FragmentOrdersBinding
import com.EcommerceApplication.ui.product.ProductViewModel
import com.EcommerceApplication.util.handleApiError
import com.example.kotlinproject.ui.base.BaseFragment
import com.kadirkuruca.newsapp.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class OrdersFragment : BaseFragment<FragmentOrdersBinding>(),
    OrderAdapter.onOrderClickListnener {
    private  val TAG = "OrdersFragment"
    @Inject
    lateinit var viewModel: OrderViewModel
    lateinit var adapter: OrderAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.getOrders()
        viewModel.getOrders.observe(viewLifecycleOwner, Observer {
                 when (it) {
                    is Resource.Success -> {
                        adapter.setData(it.value.data.data)
                        Log.d(TAG, "setupObservers: Success")
                    }
                    is Resource.Loading -> {
                        Log.d(TAG, "setupObservers: Loading")
                    }
                    is Resource.Failure -> handleApiError(it) {
                        Log.d(TAG, "setupObservers: Failure")
                        setupObservers()
                    }

                }

        })

    }


    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentOrdersBinding
        = FragmentOrdersBinding.inflate(inflater,container,false)

    private fun setupRecyclerView() {
        adapter = OrderAdapter()
         binding.rvOrders.adapter = adapter
    }

    override fun onClickItem(id: Int) {
        val action = OrdersFragmentDirections.actionOrdersFragmentToOrderDetailsFragment(id.toString())
        findNavController().navigate(action)

    }
}