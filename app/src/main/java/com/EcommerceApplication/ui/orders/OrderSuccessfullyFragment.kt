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
import kotlin.math.log

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

        binding.orderConstraintGroup.visibility = View.VISIBLE
        binding.loaderLayout.loaderCard.visibility = View.GONE
        binding.redirectHomeTimerTv.text =
            getString(R.string.redirect_home_timer_text, "5")
        countDownTimer.start()
      //  setObservable()

    }

    private fun setObservable() {
        args.orderId?.let { viewModel.orderDetalils(it) }
        viewModel.orderDetails.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    Log.d(TAG, "setObservable: order details ${it.value.data}")
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
}
