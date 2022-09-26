package com.EcommerceApplication.ui.orders

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.EcommerceApplication.R
import com.EcommerceApplication.databinding.FragmentSelectPaymentOrdersBinding
import com.EcommerceApplication.ui.Adrerss.getAddress.AddressesFragmentDirections
import com.example.kotlinproject.ui.base.BaseFragment
import com.kadirkuruca.newsapp.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SelectPaymentOrderFragment : BaseFragment<FragmentSelectPaymentOrdersBinding>() {
    private val TAG = "SelectPaymentOrderFragm"
    private val args by navArgs<SelectPaymentOrderFragmentArgs>()
    var methodpayment = 1
    var addressId: Int = 0

    @Inject
    lateinit var viewModel: OrderViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hideProgress(binding.loaderLayout)
        Log.d(TAG, "onViewCreated: ${args.selectAddressId}")

        binding.radioGroup.setOnCheckedChangeListener { radioGroup, checkedId ->
            if (checkedId == R.id.rb_paiement_when_recieving) {
                methodpayment = 1
            }

            if (checkedId == R.id.rb_paiement_by_credit_card) {
                methodpayment = 2
            }
        }

        addressId = args.selectAddressId.toInt()



        binding.buttoCompleteOrder.setOnClickListener {
            Log.d(TAG, "onViewCreated: addressId = ${addressId}")
            Log.d(TAG, "onViewCreated: methodpayment = ${methodpayment}")
            addOrderObservable()
        }
    }
/*findNavController().navigate(R.id.action_selectPaymentOrderFragment_to_orderSuccessfullyFragment)*/

    private fun addOrderObservable() {
        viewModel.addOrder(addressId, methodpayment, false)
        viewModel.addOrders.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {

                    val action = SelectPaymentOrderFragmentDirections.actionSelectPaymentOrderFragmentToOrderSuccessfullyFragment(it.value.data.id.toString())
                    findNavController().navigate(action)
                }
                is Resource.Loading -> {
                    Log.d(TAG, "orderObservable: Loading")
                }
                is Resource.Failure -> {
                    Log.d(TAG, "orderObservable: Failure")
                }
            }
        })
    }


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentSelectPaymentOrdersBinding =
        FragmentSelectPaymentOrdersBinding.inflate(inflater, container, false)

}