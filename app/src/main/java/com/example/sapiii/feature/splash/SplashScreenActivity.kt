package com.example.sapiii.feature.splash

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.sapiii.R
import com.example.sapiii.base.BaseActivity

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
    }

    override fun onStart() {
        super.onStart()
        lifecycleScope.launchWhenCreated {
            if (checkCurrentUserSession()) {
                goToHomepage()
            }
        }
    }
}