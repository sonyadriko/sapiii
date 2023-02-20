package com.example.sapiii.base

import android.app.ProgressDialog
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.sapiii.R

abstract class BaseFragment: Fragment() {
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        progressDialog = ProgressDialog(context)
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
}