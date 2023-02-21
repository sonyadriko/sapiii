package com.example.sapiii.feature.auth.viewmodel.view

import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.activity.viewModels
import com.example.sapiii.base.BaseAuthActivity
import com.example.sapiii.databinding.ActivityRegisterBinding
import com.example.sapiii.feature.auth.viewmodel.AuthViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class RegisterActivity : BaseAuthActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: AuthViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        database = FirebaseDatabase.getInstance()

        initListener()
    }

    private fun initListener() = with(binding) {

        btnRegister.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = etPassword.text.toString()
            val confirmPassword = etConfpassword.text.toString()

            if (password != confirmPassword) {
                showToast("Password tidak sama!")
                return@setOnClickListener
            }



            showProgressDialog()

            register2(email = email, password = password) { isSuccess ->
                if (isSuccess) {


                } else showToast("Register gagal")
            }
        }
    }

    private fun register2(email: String, password: String, callback: (isSuccess: Boolean) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                val user = auth.currentUser
                val uid = user?.uid
                if (uid != null) {
                    saveUserToDatabase(uid)
                }
//                callback(it.isSuccessful)
            }
    }

    private fun saveUserToDatabase(uid: String) {
        val nama = binding.etNama.text.toString()
        val nomor_hp = binding.etNotelp.text.toString()
        val role = binding.spinnerRoleRegister.selectedItem.toString()

        val user = hashMapOf(
            "email" to auth.currentUser?.email,
            "nama" to nama,
            "nomor_telepon" to nomor_hp,
            "roles" to role
        )
        database.reference.child("Users").child(uid).setValue(user)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    showToast("Register Berhasil")
                    goToLoginActivity()
                } else {
                    showToast("Register gagal")
                }

            }


    }


}