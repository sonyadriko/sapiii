package com.example.sapiii.feature.auth.view

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.sapiii.base.BaseAuthActivity
import com.example.sapiii.databinding.ActivityRegisterBinding
import com.example.sapiii.domain.User
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RegisterActivity : BaseAuthActivity() {
    private lateinit var binding: ActivityRegisterBinding
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
            val nama = etNama.text.toString()
            val noTelepon = etNotelp.text.toString()
            val role = spinnerRoleRegister.selectedItem.toString().lowercase()
            val confirmPassword = etConfpassword.text.toString()
            val userDomain = User(
                email = email,
                password = password,
                nama = nama,
                noTelepon = noTelepon,
                role = role
            )

            if (password != confirmPassword) {
                showToast("Password tidak sama!")
                dismissProgressDialog()
                return@setOnClickListener
            }

            showProgressDialog()
            register(userDomain = userDomain) { isSuccess ->
                dismissProgressDialog()
                if (isSuccess) onSuccess()
                else showToast("Register gagal")
            }
        }
    }

    private fun onSuccess() {
        lifecycleScope.launch {
            showToast("Register Berhasil")
            delay(500)
            goToLoginActivity()
        }
    }
}