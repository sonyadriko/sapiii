package com.example.sapiii.feature.ternakku.view

import android.os.Bundle
import androidx.activity.viewModels
import com.example.sapiii.base.BaseAuthActivity
import com.example.sapiii.databinding.ActivityRegisterBinding
import com.example.sapiii.feature.auth.viewmodel.AuthViewModel

class RegisterActivity : BaseAuthActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: AuthViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListener()
    }

    private fun initListener() = with(binding) {
        btnRegister.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            val confirmPassword = etConfpassword.text.toString()

            if (password != confirmPassword) {
                showToast("Password tidak sama!")
                return@setOnClickListener
            }

            showProgressDialog()
            register(email = email, password = password) { isSuccess ->
                if (isSuccess) {
                    showToast("Register Berhasil")
                    goToLoginActivity()
                } else showToast("Register gagal")
            }
        }
    }
}