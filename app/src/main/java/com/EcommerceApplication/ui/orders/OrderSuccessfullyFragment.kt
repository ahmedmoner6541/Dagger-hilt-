package com.EcommerceApplication.ui.orders

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.EcommerceApplication.R
import com.EcommerceApplication.databinding.FragmentOrderSuccessfullyBinding
import com.EcommerceApplication.util.handleApiError
import com.example.kotlinproject.ui.base.BaseFragment
import com.kadirkuruca.newsapp.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OrderSuccessfullyFragment : BaseFragment<FragmentOrderSuccessfullyBinding>() {
    private val TAG = "AddOrderFragment"
    private val args by navArgs<OrderSuccessfullyFragmentArgs>()

    @Inject
    lateinit var viewModel: OrderViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backToHomeBtn.setOnClickListener {
            findNavController().navigate(R.id.action_orderSuccessfullyFragment_to_homeFragment)
        }
        setObservable()

    }

    private fun setObservable() {
        viewModel.orderDetalils(args.orderId)
        viewModel.orderDetails.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    binding.orderConstraintGroup.visibility = View.VISIBLE
                    binding.loaderLayout.loaderCard.visibility = View.GONE
                    binding.redirectHomeTimerTv.text =
                        getString(R.string.redirect_home_timer_text, "5")
                    countDownTimer.start()
                }
                is Resource.Loading -> {
                    binding.loaderLayout.loaderCard.visibility = View.VISIBLE
                    Log.d(TAG, "deleteAddress: Loading")
                }
                is Resource.Failure -> {
                    Log.d(TAG, "deleteAddress: Failure")
                    binding.loaderLayout.loaderCard.visibility = View.GONE
                    handleApiError(it) { setObservable() }
                }
            }
        })
    }

    private val countDownTimer = object : CountDownTimer(5000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            val sec = millisUntilFinished / 1000
            binding.redirectHomeTimerTv.text =
                getString(R.string.redirect_home_timer_text, sec.toString())
        }

        override fun onFinish() {
            findNavController().navigate(R.id.action_orderSuccessfullyFragment_to_homeFragment)
        }
    }

    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentOrderSuccessfullyBinding =
        FragmentOrderSuccessfullyBinding.inflate(inflater, container, false)
}/*    private fun setviews() {
        var addressName = args.address.name
        var addressId = args.address.id
        var addressCity = args.address.city
        var addressDetails = args.address.details
        var addressNore = args.address.notes
        var addressRegion = args.address.region

        var orderPaymentMethod = args.addOrderRequst.paymentMethod
        var orderUsePoints = args.addOrderRequst.usePoints
        var orderDiscound = args.addOrderRequst.addressId

        var orderPromocode:Int = 1


//// باقي التفاصيل في صفحه تفاصيل الاوردر

        binding.tvNameOrder.text = addressName
        binding.tvNameOrder.text = addressName
        binding.tvAddressOrder.text = "${addressCity + "," + addressNore + "," + addressDetails + "," + addressRegion}"
        binding.tvThpeOfPaymentOrder.text = orderPaymentMethod.toString()
        binding.tvDiscountOrder.text = orderDiscound.toString()
        binding.radioButtonpromocode.setOnClickListener { view ->
            if (view.id == R.id.radioButtonpromocode) {
                binding.etPromocode.visable(true)
            } else {
                binding.etPromocode.visable(false)
            }
        }

        var promoFromEt:String = binding.etPromocode.text.toString()

        orderPromocode =  1


        val addOrderRequest = AddOrderRequest(addressId, orderPaymentMethod, orderUsePoints, orderPromocode)
        binding.buttontoSuccess.setOnClickListener {
            viewModel.addOrder(addOrderRequest)
        }

        setObservable(addOrderRequest)

    }*/