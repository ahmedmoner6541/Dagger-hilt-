package com.EcommerceApplication.ui.Adrerss.upsert

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.EcommerceApplication.R
import com.EcommerceApplication.data.local.shared.TokenManager
import com.EcommerceApplication.data.remote.request.addAddress.AddAddressRequest
import com.EcommerceApplication.data.remote.request.updateAddress.UpdateAddressRequest
import com.EcommerceApplication.data.remote.response.getAdress.Addresses
import com.EcommerceApplication.databinding.FragmentAddUpdateAddressBinding
import com.EcommerceApplication.ui.Adrerss.AddressViewModel
import com.EcommerceApplication.util.handleApiError
import com.example.kotlinproject.ui.base.BaseFragment
import com.kadirkuruca.newsapp.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddUpdateAddressFragment : BaseFragment<FragmentAddUpdateAddressBinding>() {
    private val TAG = "AddUpdateAddressFragmen"

    @Inject
    lateinit var viewModel: AddressViewModel

    // TODO:  
    @Inject
    lateinit var tokenManager: TokenManager

    private val args by navArgs<AddUpdateAddressFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        Log.d(TAG, "onViewCreated: token = ${tokenManager.getToken().toString()}")


        if (args.isAdd) {
            binding.button.setOnClickListener {
                onAddAddress()
            }
        } else {
            setViewsUpdate(args.updateAddress!!)
            binding.button.setOnClickListener {
                onUpdateAddress(args.updateAddress!!)
            }
        }


        setupObserversAdd()
        setupObserversUpdate()
    }

    private fun setupObserversUpdate() {
        viewModel.updateAddress.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    Log.d(TAG, "setupObservers: Success${it}")
                    hideProgress(binding.loaderLayout)
                    findNavController().navigate(R.id.action_addUpdateAddressFragment_to_addressesFragment)
                }
                is Resource.Loading -> {
                    Log.d(TAG, "setupObservers: Loading")
                    showProgress(binding.loaderLayout)
                }
                is Resource.Failure -> {
                    Log.d(TAG, "setupObservers: Failure")
                    hideProgress(binding.loaderLayout)
                    handleApiError(it) { setupObserversUpdate() }
                }
            }
        })
    }

    private fun onUpdateAddress(updateAddress: Addresses) {

        val newName = binding.etNamePersonAddress.text.toString()
        val newCity = binding.etNamePersonAddress.text.toString()
        val newRegion = binding.etNamePersonAddress.text.toString()
        val newDetails = binding.etNamePersonAddress.text.toString()
        val newNotes = binding.etNamePersonAddress.text.toString()

        var newAddress = UpdateAddressRequest(newName, newCity, newRegion, newDetails, 0.0, 0.0, newNotes)

        viewModel.updateAddress(updateAddress.id, newAddress)
    }

    private fun setViewsUpdate(updateAddress: Addresses) {
        binding.etNamePersonAddress.setText(updateAddress.name)
        binding.etCityPirsonAddress.setText(updateAddress.city)
        binding.etPhonePersonAddress.setText("")
        binding.etRegionsPersonAddress.setText(updateAddress.region)
        binding.etNotePesonAddress.setText(updateAddress.notes)
        binding.etDetailsPesonAddress.setText(updateAddress.details)

    }


    private fun setupObserversAdd() {
        viewModel.addNewAddresses.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    Log.d(TAG, "setupObservers: Success${it}")
                    hideProgress(binding.loaderLayout)
                    findNavController().navigate(R.id.action_addUpdateAddressFragment_to_addressesFragment)
                }
                is Resource.Loading -> {
                    Log.d(TAG, "setupObservers: Loading")
                    showProgress(binding.loaderLayout)
                }
                is Resource.Failure -> {
                    Log.d(TAG, "setupObservers: Failure")
                    hideProgress(binding.loaderLayout)
                    handleApiError(it) { setupObserversAdd() }
                }
            }
        })
    }


    private fun onAddAddress() {
        //todo check if any variabel is null or no
        val name = binding.etNamePersonAddress.text.toString()
        val city = binding.etNamePersonAddress.text.toString()
        val region = binding.etNamePersonAddress.text.toString()
        val details = binding.etNamePersonAddress.text.toString()
        val notes = binding.etNamePersonAddress.text.toString()

        var newAddress = AddAddressRequest(name, city, region, details, 0.0, 0.0, notes)
        viewModel.addAddress(newAddress)


    }

    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentAddUpdateAddressBinding =
        FragmentAddUpdateAddressBinding.inflate(inflater, container, false)


}