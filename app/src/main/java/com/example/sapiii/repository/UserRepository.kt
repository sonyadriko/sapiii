package com.example.sapiii.repository

import android.content.Context
import com.example.sapiii.constanst.Constant.Role

class UserRepository constructor(
    context: Context
) {
    companion object {
        const val ROLE = "role"
    }

    private val sharedPreferences = context.getSharedPreferences("${context.packageName}_pref", Context.MODE_PRIVATE)

    var role: Role? = null
        get() = Role.valueOf(sharedPreferences.getString(ROLE, "").toString())
        set(value) {
            field = value
            sharedPreferences.edit().apply {
                remove(ROLE)
                putString(ROLE, value.toString().lowercase())
                apply()
            }
        }
}