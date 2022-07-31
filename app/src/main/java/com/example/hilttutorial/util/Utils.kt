package com.example.hilttutorial.util

import android.app.Activity
import android.content.Intent
import android.view.View


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


/*
fun Fragment.handleApiError(
    failure: Resource.Failure,
    retry: (() -> Unit)? = null
)
{
    when {
        failure.isNetworkError -> requireView().Snackbar(
            "Please check your internet connection",
            retry
        )
        else -> {
            val error = failure.errorResponse?.string().toString()
            if (this is LoginFragment) {
                requireView().Snackbar(error)
             //   requireView().Snackbar("You've entered incorrect email or password",retry)
            } else {
                (this as BaseFragment<*, *, *>).logout()
            }

        }




       */
/* failure.errorResponse..string -> {
            if (this is LoginFragment) {
                requireView().Snackbar("You've entered incorrect email or password",retry)
            } else {
                (this as BaseFragment<*, *, *>).logout()
            }

        }

        failure.errorCode == 404 -> {
            if (this is LoginFragment) {
                requireView().Snackbar("You've entered incorrect email or password")
            } else {
                (this as BaseFragment<*, *, *>).logout()
            }
        } *//*


    }
}
*/
