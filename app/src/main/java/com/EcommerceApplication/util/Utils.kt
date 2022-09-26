package com.EcommerceApplication.util

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.EcommerceApplication.ui.auth.login.LoginFragment
import com.EcommerceApplication.ui.auth.register.RegisterFragment
import com.example.kotlinproject.ui.base.BaseFragment
import com.kadirkuruca.newsapp.util.Resource
import java.util.*


//startNewActivity
fun <A : Activity> Activity.startNewActivity(activity: Class<A>) {
    Intent(this, activity).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }

}

fun View.visable(isVisable: Boolean) {
    visibility = if (isVisable) View.VISIBLE else View.GONE
}

fun View.enable(enabled: Boolean) {
    isEnabled = enabled
    alpha = if (enabled) 1f else 0.5f
}
inline fun Fragment.toast(message: () -> String) {
    Toast.makeText(this.context, message(), Toast.LENGTH_LONG).show()
}

fun View.Snackbar(message: String, action: (() -> Unit)? = null) {
    val snackbar = com.google.android.material.snackbar.Snackbar.make(this,
        message,
        com.google.android.material.snackbar.Snackbar.LENGTH_LONG)

    action?.let {
        snackbar.setAction("Retry") {

        }
    }
    snackbar.show()
}


fun Fragment.handleApiError(
    failure: Resource.Failure,
    retry: (() -> Unit)? = null
) {
    when {
        failure.isNetworkError -> requireView().Snackbar(
            "Please check your internet connection",
            retry
        )
        failure.errorCode == 401 -> {
            if (this is LoginFragment) {
                requireView().Snackbar("You've entered incorrect email or password")
            } else {
                Log.d("TAG", "handleApiError: 401 else Utils")
            }
        }
        else -> {
            val error = failure.errorResponse?.string().toString()
            requireView().Snackbar(error)
        }
    }



}


