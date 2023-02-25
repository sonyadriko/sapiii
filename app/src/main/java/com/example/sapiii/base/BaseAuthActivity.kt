package com.example.sapiii.base

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sapiii.constanst.Constant.REFERENCE_USER
import com.example.sapiii.domain.User
import com.example.sapiii.feature.HomeActivity
import com.example.sapiii.feature.auth.view.LoginActivity
import com.example.sapiii.feature.auth.view.RegisterActivity
import com.example.sapiii.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

abstract class BaseAuthActivity : AppCompatActivity() {
    private val userRepository = UserRepository(context = applicationContext)
    lateinit var auth: FirebaseAuth
    lateinit var database: FirebaseDatabase
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        database = FirebaseDatabase.getInstance()
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

    fun register(userDomain: User, callback: (isSuccess: Boolean) -> Unit) {
        auth.createUserWithEmailAndPassword(userDomain.email, userDomain.password)
            .addOnCompleteListener {
                val user = auth.currentUser
                val uid = user?.uid
                if (uid != null) {
                    saveUserToDatabase(uid, userDomain) { isSuccess ->
                        callback(isSuccess)
                    }
                } else callback(false)
            }
    }

    private fun saveUserToDatabase(uid: String, userDomain: User, onComplete: (isSuccess: Boolean) -> Unit) {
        database.reference.child(REFERENCE_USER).child(uid).setValue(userDomain.toMap())
            .addOnCompleteListener { task ->
                onComplete(task.isSuccessful)
            }
    }

    fun goToRegister() {
        startActivity(Intent(this, RegisterActivity::class.java))
    }

    fun goToLoginActivity() {
        startActivity(Intent(this, LoginActivity::class.java))
    }
}