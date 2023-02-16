package com.example.sapiii.util

import com.example.sapiii.constanst.Constant.Role

fun String.toRole(): Role {
    return when (this.lowercase()) {
        Role.PETERNARK.name.lowercase() -> {
            Role.PETERNARK
        }
        Role.INVESTOR.name.lowercase() -> {
            Role.INVESTOR
        }
        Role.ADMIN.name.lowercase() -> {
            Role.ADMIN
        }
        else -> Role.ADMIN
    }
}

fun String.toFloatNew(): Float {
    return this.replace(",", ".").toFloat()
}