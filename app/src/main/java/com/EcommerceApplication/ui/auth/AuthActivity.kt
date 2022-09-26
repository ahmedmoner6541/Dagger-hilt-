package com.EcommerceApplication.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.EcommerceApplication.R
import com.EcommerceApplication.ui.home.HomeActivity
import com.EcommerceApplication.data.local.shared.TokenManager
import com.EcommerceApplication.util.startNewActivity

import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {

    @Inject
    lateinit var tokenManager: TokenManager


    private val TAG = "AuthActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        Log.d(TAG, "onCreate:tokenManager ${tokenManager.getToken()}")

         var  tokenManager2: TokenManager
        var token2 = tokenManager.getToken().toString()
        Log.d(TAG, "onCreate:tokenMana token 2 = ${token2}")

        if (tokenManager.getToken() != null) {
            startNewActivity(HomeActivity::class.java)        }
     //   val userPreferences = AppDataStoreManager(this)

/*
        userPreferences.authToken.asLiveData().observe(this, Observer {
            Log.d(TAG, "onCreate: ${it}")
            if (it != null) {
                startNewActivity(HomeActivity::class.java)
                Log.d(TAG, "onCreate: if")
            }


       if (it == null) {
                startNewActivity(AuthActivity::class.java)
                Log.d(TAG, "onCreate: if")
            }
            else {
                startNewActivity(HomeActivity::class.java)
                Log.d(TAG, "onCreate: else")

            } 

        }) */
    }
}