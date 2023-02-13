package com.example.sapiii.feature.auth.viewmodel

import androidx.lifecycle.ViewModel
import com.example.sapiii.domain.User

class AuthViewModel : ViewModel() {
    var user = User(
        username = "",
        email = "",
        phoneNumber = "",
        password = "",
        role = ""
    )
        private set

    fun setData(username: String, email: String, number: String, password: String, role: String) {

    }
}