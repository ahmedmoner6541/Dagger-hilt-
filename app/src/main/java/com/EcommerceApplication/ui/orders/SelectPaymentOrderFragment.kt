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
import com.EcommerceApplication.data.remote.response.toProductAdapterModel
import com.EcommerceApplication.databinding.FragmentSelectPaymentOrdersBinding
import com.EcommerceApplication.ui.Adrerss.getAddress.AddressesFragmentDirections
import com.EcommerceApplication.ui.product.ProductViewModel
import com.EcommerceApplication.util.Snackbar
import com.EcommerceApplication.util.visable
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
                    hideProgress(binding.loaderLayout)

                    Log.d(TAG, "orderObservable: success${it.value.data.id}")
                    val action =
                        SelectPaymentOrderFragmentDirections.actionSelectPaymentOrderFragmentToOrderSuccessfullyFragment(
                            it.value.data.id.toString()
                        )
                    findNavController().navigate(action)
                }

                is Resource.Loading -> {

                    showProgress(binding.loaderLayout)
                    Log.d(TAG, "orderObservable: Loading")
                }

                is Resource.Failure -> {
                    showProgress(binding.loaderLayout)
                    addOrderObservable()

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


fun getFragmentBinding(
    inflater: LayoutInflater,
    container: ViewGroup?,
): FragmentSelectPaymentOrdersBinding =
    FragmentSelectPaymentOrdersBinding.inflate(inflater, container, false)


/*
*
* package com.EcommerceApplication.ui.orders

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
import com.EcommerceApplication.data.remote.response.toProductAdapterModel
import com.EcommerceApplication.databinding.FragmentSelectPaymentOrdersBinding
import com.EcommerceApplication.ui.Adrerss.getAddress.AddressesFragmentDirections
import com.EcommerceApplication.ui.product.ProductViewModel
import com.EcommerceApplication.util.Snackbar
import com.EcommerceApplication.util.visable
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
    lateinit var viewModelProduct: ProductViewModel
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
            viewModelProduct.cart()
            viewModelProduct.cart.observe(viewLifecycleOwner, Observer {
                when (it) {
                    is Resource.Success -> {
                        addOrderObservable()
                        hideProgress(binding.loaderLayout)
                  /*      if (it.value.data.total.toInt()==0){
                            requireView().Snackbar("لا يمكن اضافه اوردر وسله المنتحات فارغه")
                            Log.d(TAG, "onViewCreated: cartHasProducts")
                            findNavController().navigate(R.id.action_selectPaymentOrderFragment_to_homeFragment)

                        }else{
                        }*/
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
    }
/*findNavController().navigate(R.id.action_selectPaymentOrderFragment_to_orderSuccessfullyFragment)*/

    private fun addOrderObservable() {
        viewModel.addOrder(addressId, methodpayment, false)
        viewModel.addOrders.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    hideProgress(binding.loaderLayout)
                     findNavController().navigate(R.id.action_selectPaymentOrderFragment_to_homeFragment)

                }
                is Resource.Loading -> {
                    //showProgress(binding.loaderLayout)
                    Log.d(TAG, "orderObservable: Loading")
                    showProgress(binding.loaderLayout)
                }
                is Resource.Failure -> {
                    Log.d(TAG, "orderObservable: Failure")
                    showProgress(binding.loaderLayout)
                    addOrderObservable()

                }
            }
        })
    }






    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentSelectPaymentOrdersBinding =
        FragmentSelectPaymentOrdersBinding.inflate(inflater, container, false)

}*/