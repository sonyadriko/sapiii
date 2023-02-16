package com.example.sapiii.base

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sapiii.feature.HomeActivity
import com.example.sapiii.feature.auth.viewmodel.view.LoginActivity
import com.example.sapiii.feature.auth.viewmodel.view.RegisterActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

abstract class BaseAuthActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        progressDialog = ProgressDialog(this)
    }

    fun login(email: String, password: String, doOnFailed: () -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    startActivity(
                        Intent(this, HomeActivity::class.java)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    )
                } else doOnFailed()
            }
    }

    fun showToast(message: String, length: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this, message, length).show()
    }

    fun showProgressDialog() {
        progressDialog.setMessage("Loading")
        progressDialog.setCancelable(false)
        progressDialog.show()
    }

    fun dismissProgressDialog() = progressDialog.dismiss()

    fun register(email: String, password: String, callback: (isSuccess: Boolean) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                callback(it.isSuccessful)
            }
    }

    fun goToRegister() {
        startActivity(Intent(this, RegisterActivity::class.java))
    }

    fun goToLoginActivity() {
        startActivity(Intent(this, LoginActivity::class.java))
    }
}