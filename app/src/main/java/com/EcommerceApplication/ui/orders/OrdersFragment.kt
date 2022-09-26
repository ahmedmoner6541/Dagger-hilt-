package com.EcommerceApplication.ui.orders

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.EcommerceApplication.databinding.FragmentOrdersBinding
import com.EcommerceApplication.util.handleApiError
import com.example.kotlinproject.ui.base.BaseFragment
import com.kadirkuruca.newsapp.util.Resource
import dagger.hilt.android.AndroidEntryPoint
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
                        showProgress(binding.loaderLayout)
                        hideProgress(binding.loaderLayout)

                    }
                    is Resource.Loading -> {
                        Log.d(TAG, "setupObservers: Loading")
                        showProgress(binding.loaderLayout)
                    }
                    is Resource.Failure -> handleApiError(it) {
                        showProgress(binding.loaderLayout)
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
        adapter.onOrderclickListnener = this

    }

    override fun onClickItem(id: Int) {
        val action = OrdersFragmentDirections.actionOrdersFragmentToOrderDetailsFragment(id.toString())
        findNavController().navigate(action)

    }
}