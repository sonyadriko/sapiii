package com.example.sapiii.base

import android.app.ProgressDialog
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment

abstract class BaseFragment: Fragment() {
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        progressDialog = ProgressDialog(context)
    }

    fun showToast(message: String, length: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(context, message, length).show()
    }

    fun showProgressDialog() {
        progressDialog.setMessage("Loading")
        progressDialog.setCancelable(false)
        progressDialog.show()
    }

    fun dismissProgressDialog() = progressDialog.dismiss()
}