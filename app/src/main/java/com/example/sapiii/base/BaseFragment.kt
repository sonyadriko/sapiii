package com.example.sapiii.base

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.sapiii.R
import com.example.sapiii.feature.auth.view.LoginActivity
import com.example.sapiii.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

abstract class BaseFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    lateinit var userRepository: UserRepository
    private lateinit var progressDialog: ProgressDialog
    lateinit var alertDialog: AlertDialog.Builder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        progressDialog = ProgressDialog(context)
        alertDialog = AlertDialog.Builder(requireActivity())
        userRepository = UserRepository.getInstance(requireActivity())
    }

    override fun onStart() {
        super.onStart()
        if (!checkCurrentUserSession()) {
            showToast("Anda harus login terlebih dahulu")
            logout()
        }
    }

    fun showToast(message: String? = null, length: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(context, message ?: getString(R.string.something_wrong), length).show()
    }

    fun showProgressDialog() {
        progressDialog.setMessage("Loading")
        progressDialog.setCancelable(false)
        progressDialog.show()
    }

    fun dismissProgressDialog() = progressDialog.dismiss()

    private fun checkCurrentUserSession(): Boolean {
        val uid = userRepository.uid
        return auth.currentUser != null
                && requireNotNull(uid) == requireNotNull(auth.currentUser).uid
    }

    fun logout() {
        if (auth.currentUser != null) {
            userRepository.erase()
            auth.signOut()
        }

        startActivity(
            Intent(requireActivity(), LoginActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        )
    }

}