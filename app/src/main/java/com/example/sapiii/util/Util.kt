package com.example.sapiii.util

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.sapiii.constanst.Constant.Role
import com.example.sapiii.domain.Artikel
import com.example.sapiii.domain.Kambing
import com.example.sapiii.domain.Sapi
import com.google.firebase.database.DataSnapshot
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder


fun String.toRole(): Role? {
    return Role.values().find {
        it.name.lowercase() == this
    }
}

fun String.toFloatNew(): Float {
    return this.replace(",", ".").toFloat()
}

fun DataSnapshot.toSapiDomain(): Sapi {
    return getValue(Sapi::class.java)!!
}

fun DataSnapshot.toKambingDomain(): Kambing {
    return getValue(Kambing::class.java)!!
}

fun DataSnapshot.toArtikelDomain(): Artikel {
    return getValue(Artikel::class.java)!!
}

fun View.gone() {
    visibility = View.GONE
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun ImageView.generateBarcode(content: String) {
    try {
        val glide = Glide.with(this)
        val barcodeEncoder = BarcodeEncoder()
        val bitmap =
            barcodeEncoder.encodeBitmap(content, BarcodeFormat.QR_CODE, this.width, this.height)
        glide.load(bitmap).into(this)
    } catch (e: Exception) {
        Log.e("QR GENERATOR", e.message.toString())
    }
}