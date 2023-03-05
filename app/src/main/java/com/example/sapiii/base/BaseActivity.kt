package com.example.sapiii.base

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sapiii.R
import com.example.sapiii.feature.HomeActivity
import com.example.sapiii.feature.auth.view.LoginActivity
import com.example.sapiii.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

abstract class BaseActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    lateinit var database: FirebaseDatabase
    private lateinit var progressDialog: ProgressDialog
    lateinit var userRepository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        database = FirebaseDatabase.getInstance()
        progressDialog = ProgressDialog(this)
        userRepository = UserRepository.getInstance(this)
    }

    override fun onStart() {
        super.onStart()
        if (!checkCurrentUserSession()) {
            showToast("Anda harus login terlebih dahulu")
            logout()
        }
    }

    fun checkCurrentUserSession(): Boolean {
        val uid = userRepository.uid
        return auth.currentUser != null
                && uid == auth.currentUser?.uid
    }

    fun showToast(message: String? = null, length: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this, message ?: getString(R.string.something_wrong), length).show()
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
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            )
        }
    }

    fun logout() {
        if (checkCurrentUserSession()) {
            auth.signOut()
            userRepository.erase()
        }

        startActivity(
            Intent(this, LoginActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        )
    }
}