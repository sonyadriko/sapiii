package com.example.sapiii.base

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sapiii.feature.HomeActivity
import com.example.sapiii.feature.auth.viewmodel.view.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

abstract class BaseActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    lateinit var firebaseDB: FirebaseDatabase
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        firebaseDB = FirebaseDatabase.getInstance()
        progressDialog = ProgressDialog(this)
    }

    override fun onStart() {
        super.onStart()
        if (!checkCurrentUserSession()) {
            logout()
        }
    }

    fun checkCurrentUserSession(): Boolean {
        return auth.currentUser != null
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

    fun goToHomepage() {
        if (checkCurrentUserSession()) {
            startActivity(
                Intent(this, HomeActivity::class.java)
            )
        }
    }

    fun logout() {
        if (auth.currentUser != null) auth.signOut()

        startActivity(
            Intent(this, LoginActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        )
    }
}