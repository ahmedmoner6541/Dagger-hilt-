package com.EcommerceApplication.ui.Adrerss.getAddress

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.EcommerceApplication.data.models.Product
import com.EcommerceApplication.data.remote.response.getAdress.Addresses
import com.EcommerceApplication.databinding.FragmentAddressesBinding
import com.EcommerceApplication.ui.Adrerss.AddressViewModel
import com.EcommerceApplication.util.handleApiError
import com.EcommerceApplication.util.toast
import com.EcommerceApplication.util.visable
import com.example.kotlinproject.ui.base.BaseFragment
import com.kadirkuruca.newsapp.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddressesFragment : BaseFragment<FragmentAddressesBinding>(),
    AddressesAdapter.OnAddressClick {
    private   val TAG = "AddressesFragment"
    @Inject
    lateinit var viewModel: AddressViewModel

    lateinit var adapter: AddressesAdapter




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()


        binding.addFabAddAddress.setOnClickListener {
            val action =  AddressesFragmentDirections.actionAddressesFragmentToAddUpdateAddressFragment(null,true)
            findNavController().navigate(action)
          }

        binding.addressNextBtn.setOnClickListener {    toast { "يجب اختيار العنوان اولا" } }


    }


    private fun setupObservers(){

        viewModel.getAllAddresses()
        viewModel.getAddresses.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Success ->{
                     if (it.value.data.data==null){
                        binding.addressEmptyTextView.visable(true)
                    }
                    hideProgress(binding.loaderLayout)
                    adapter.setDate(it.value.data.data)
                }
                is Resource.Loading ->{
                    Log.d(TAG, "setupObservers: Loading")
                    showProgress(binding.loaderLayout)
                }
                is Resource.Failure ->{
                    hideProgress(binding.loaderLayout)
                    Log.d(TAG, "setupObservers: Failure")
                    handleApiError(it) { setupObservers() }
                }
            }
        })
    }

    private fun setupRecyclerView() {
        adapter = AddressesAdapter()
        adapter.onAddressClick= this
        binding.addressAddressesRecyclerView.adapter = adapter
    }

    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentAddressesBinding  =
        FragmentAddressesBinding.inflate(inflater,container,false)

    override fun updateAddress(cuttentItem: Addresses) {

        val action =  AddressesFragmentDirections.actionAddressesFragmentToAddUpdateAddressFragment(cuttentItem,false)
        findNavController().navigate(action)

    }


    override fun deleteAddress(cuttentItem: Addresses ) {

        viewModel.deleteAddress(cuttentItem.id)
        viewModel.deleteAddress.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    Log.d(TAG, "deleteAddress: ${it.value.data.name}")
                    hideProgress(binding.loaderLayout)
                    setupObservers()
                 }
                is Resource.Loading -> {
                    Log.d(TAG, "deleteAddress: Loading")
                    showProgress(binding.loaderLayout)
                }
                is Resource.Failure -> {
                    Log.d(TAG, "deleteAddress: Failure")
                    showProgress(binding.loaderLayout)
                }

            }
        })


    }

    override fun onAddressSelected(cuttentAddressId: String) {
        setOrderDetails(cuttentAddressId)


    }


    private fun setOrderDetails(cuttentAddressId: String) {
        binding.addressNextBtn.setOnClickListener {
            val action = AddressesFragmentDirections.actionAddressesFragmentToSelectPaymentOrderFragment(cuttentAddressId)
           findNavController().navigate(action)

        }


    }
/*    private fun showDeleteDialog(addressId: String) {
        context?.let {
            MaterialAlertDialogBuilder(it)
                .setTitle("title")
                .setMessage("message")
                .setNeutralButton("NeutralButto") { dialog, _ ->
                    dialog.cancel()
                }
                .setPositiveButton("PositiveButton") { dialog, _ ->

                }
                .show()
        }
    }*/
}


