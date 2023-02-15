package com.example.sapiii.feature.ternakku.view

import android.os.Bundle
import androidx.activity.viewModels
import com.example.sapiii.base.BaseAuthActivity
import com.example.sapiii.databinding.ActivityLoginBinding
import com.example.sapiii.feature.auth.viewmodel.AuthViewModel

class LoginActivity : BaseAuthActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val vm: AuthViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        setContentView(binding.root)

        initListener()
    }

    private fun initListener() = with(binding) {
        btnLogin.setOnClickListener {
            if (!etEmail.text.isNullOrEmpty() && !etPassword.text.isNullOrEmpty()) {
                showProgressDialog()
                login(etEmail.text.toString(), etPassword.text.toString(), doOnFailed = {
                    dismissProgressDialog()
                    showToast("Akun tidak ditemukan")
                })
            }
        }

        btnRegister.setOnClickListener {
            goToRegister()
        }
    }
}