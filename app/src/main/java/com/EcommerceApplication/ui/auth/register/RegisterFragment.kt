package com.EcommerceApplication.ui.auth.register

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.EcommerceApplication.R
import com.EcommerceApplication.databinding.FragmentRegisterBinding
import com.EcommerceApplication.ui.auth.AuthViewModel
import com.EcommerceApplication.data.local.shared.TokenManager
import com.EcommerceApplication.ui.home.HomeActivity
import com.EcommerceApplication.util.*
import com.example.kotlinproject.ui.base.BaseFragment
import com.kadirkuruca.newsapp.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>() {
    private val TAG = "RegisterFragment"

    @Inject
    lateinit var viewModel: AuthViewModel

    @Inject
    lateinit var tokenManager: TokenManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvGoToLoginFromRegister.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_home_nav)
        }

        binding.progressBar2.visable(false)
        binding.btnRegister.enable(false)



        viewModel.registerResopne.observe(viewLifecycleOwner, Observer {
            binding.progressBar2.visable(it is Resource.Loading)
            Log.d(TAG, "onViewCreated: data${it.data}")
            when (it) {
                is Resource.Success -> {
                    if (it.value.data == null) {
                        toast { "${it.value.message}" }
                        return@Observer
                    } else {
                        tokenManager.saveToken(it.value.data.token)
                        Toast.makeText(requireContext(), it.value.data.toString(), Toast.LENGTH_LONG).show()
                        requireActivity().startNewActivity(HomeActivity::class.java)
                    }
                }
                is Resource.Failure -> handleApiError(it) { register() }


            }
        })

        binding.etPasswordRegister.addTextChangedListener {
            val email = binding.etEmailRegister.text.toString().trim()
            binding.btnRegister.enable(email.isNotEmpty()
                    && it.toString().isNotEmpty())
            binding.btnRegister.setBackgroundColor(this.getResources().getColor(R.color.buttons));

        }

        binding.btnRegister.setOnClickListener {
            hideKeyboard(it)
            register()
        }
    }

    private fun register() {
        val name = binding.etNameRegister.text.toString().trim()
        val email = binding.etEmailRegister.text.toString().trim()
        val phone = binding.etPhoneRegister.text.toString().trim()
        val password = binding.etPasswordRegister.text.toString().trim()

        viewModel.register(email, name, password, phone)
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentRegisterBinding = FragmentRegisterBinding.inflate(inflater, container, false)


}