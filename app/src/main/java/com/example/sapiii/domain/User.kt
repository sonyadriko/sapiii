package com.example.sapiii.domain

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.Serializable

data class User(
    val nama: String = "",
    val email: String = "",
    val noTelepon: String = "",
    @Transient
    val password: String = "",
    val role: String = ""
) : Serializable {
    fun toMap(): Map<String, Any?> {
        val gson = Gson()
        val json = gson.toJson(this)
        return gson.fromJson(json, object : TypeToken<Map<String, Any>>() {}.type)
    }
}