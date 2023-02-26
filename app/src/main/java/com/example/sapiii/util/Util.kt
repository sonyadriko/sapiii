package com.example.sapiii.util

import android.view.View
import com.example.sapiii.constanst.Constant.Role
import com.example.sapiii.domain.Kambing
import com.example.sapiii.domain.Sapi
import com.google.firebase.database.DataSnapshot

fun String.toRole(): Role? {
    return Role.values().find {
        it.name.lowercase() == this
    }
}

fun String.toFloatNew(): Float {
    return this.replace(",", ".").toFloat()
}

fun DataSnapshot.toSapiDomain() : Sapi {
    return getValue(Sapi::class.java)!!
}

fun DataSnapshot.toKambingDomain() : Kambing {
    return getValue(Kambing::class.java)!!
}

fun View.gone() {
    visibility = View.GONE
}

fun View.visible() {
    visibility = View.VISIBLE
}