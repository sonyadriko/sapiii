package com.example.sapiii.domain

data class User(
    val username: String,
    val email: String,
    val phoneNumber: String,
    val password: String,
    val role: String
)