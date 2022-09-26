package com.EcommerceApplication.ui.auth.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.graphics.Color
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.EcommerceApplication.R
import com.EcommerceApplication.data.local.shared.TokenManager
import com.EcommerceApplication.databinding.FragmentLoginBinding
import com.EcommerceApplication.repository.AuthRepositoryImpl
import com.EcommerceApplication.ui.auth.AuthViewModel
import com.EcommerceApplication.ui.home.HomeActivity
import com.EcommerceApplication.util.*
import com.example.kotlinproject.ui.base.BaseFragment
import com.kadirkuruca.newsapp.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

// TODO:ال auth repository محتاج يعرف ال token ويكون عام
@AndroidEntryPoint
class LoginFragment : BaseFragment< FragmentLoginBinding>() {
    private   val TAG = "LoginFragment"
    // TODO: Rename and change types of parameters

    @Inject
    lateinit var tokenManager: TokenManager

    @Inject
    lateinit var viewModel: AuthViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvGoToRegisterFromLogin.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)

        }
        binding.progressBar.visable(false)
        binding.btnLogin.enable(false)

        viewModel.loginResponse.observe(viewLifecycleOwner, Observer {
            binding.progressBar.visable(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    lifecycleScope.launch {
                        if (it.value.status == false) {
                            toast { "${it.value.message}" }
                            return@launch
                        } else {
                            tokenManager.saveToken(it.value.data.token!!)
                            Log.d(TAG, "onViewCreated token:${it.value.data.token} ")
                            requireActivity().startNewActivity(HomeActivity::class.java)
                        }
                    }
                }
                is Resource.Failure -> handleApiError(it) { login() }
//                { toast {  it.toString() }Log.d(TAG, "onViewCreated: Failure" + it.toString()) }
            }
        })

        binding.etPasswordLogin.addTextChangedListener {
            val email = binding.etEmailLogin.text.toString().trim()
            binding.btnLogin.enable(email.isNotEmpty() && it.toString().isNotEmpty())
            binding.btnLogin.setBackgroundColor(this.getResources().getColor(R.color.buttons));

        }

        binding.btnLogin.setOnClickListener {
            hideKeyboard(it)
            login()
        }
    }

    private fun login() {
        val email: String = binding.etEmailLogin.text.toString().trim()
        val password: String = binding.etPasswordLogin.text.toString().trim()
        viewModel.login(email, password)
    }

    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentLoginBinding =
        FragmentLoginBinding.inflate(inflater, container, false)

}