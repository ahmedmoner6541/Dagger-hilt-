package com.example.kotlinproject.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.EcommerceApplication.R
import com.EcommerceApplication.data.local.shared.TokenManager
import com.EcommerceApplication.databinding.LayoutCircularLoaderBinding
import com.EcommerceApplication.util.visable
import com.google.android.material.progressindicator.CircularProgressIndicator
import kotlinx.coroutines.launch
import javax.inject.Inject


typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T
//Pull Up Method*********************************
abstract class BaseFragment
<VB : ViewBinding> : Fragment() {

    @Inject
    lateinit var userPrefrences: TokenManager


    protected lateinit var binding: VB


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // TODO:  requireContext

        binding = getFragmentBinding(inflater, container)
        lifecycleScope.launch { userPrefrences.getToken() }

        val circularLoader = binding.root.findViewById<CircularProgressIndicator>(R.id.circular_loader)
        //  val loaderFrameLayout = binding.root.findViewById<FrameLayout>(R.id.circular_loader)

        var loaderFrameLayout = LayoutCircularLoaderBinding.inflate(inflater)


        hideProgress(loaderFrameLayout)
        showProgress(loaderFrameLayout)
        return binding.root
    }

    fun hideProgress(loaderFrameLayout: LayoutCircularLoaderBinding) {
        if (loaderFrameLayout != null) {
            loaderFrameLayout.loaderFrameLayout.visable(false)
            loaderFrameLayout.circularLoader.hideAnimationBehavior

        }
    }

    fun showProgress(loaderFrameLayout: LayoutCircularLoaderBinding) {
        if (loaderFrameLayout != null) {
            loaderFrameLayout.loaderFrameLayout.visable(true)
            loaderFrameLayout.circularLoader.showAnimationBehavior

        }
    }


    fun setAppBarTitle(title:String){
        findNavController().addOnDestinationChangedListener { controller, destination, arguments ->
            destination.label =title
        }
    }
/*
    fun showProgress(test: CircularProgressIndicator?, loaderFrameLayout: LayoutCircularLoaderBinding) {
        if (test != null) {
            test.visibility = View.GONE
            test.hideAnimationBehavior
            test.indicatorDirection
        }


    }
*/


/*
  fun logout() = lifecycleScope.launch {
        val authToken = userPrefrences.getToken()

        viewModel.logout(apiUser)
        userPrefrences.clearShared()
        requireActivity().startNewActivity(AuthActivity::class.java)

    }*/


    abstract fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): VB

 /*   inline fun Fragment.toast(message: () -> String) {
        Toast.makeText(this.context, message(), Toast.LENGTH_LONG).show()
    }
*/

/*
    fun EditText.showKeyboard(
    ) {
        requestFocus()
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as
                InputMethodManager
        imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
    }
*/

    fun showKeyboard(view: View) {
        try {
            val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        } catch (e: Exception) {

        }
    }fun hideKeyboard(view: View) {
        try {
            val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        } catch (e: Exception) {

        }
    }


}
