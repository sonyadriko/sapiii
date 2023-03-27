package com.example.sapiii.util

import android.util.Log
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.sapiii.constanst.Constant.Role
import com.example.sapiii.domain.*
import com.example.sapiii.repository.KehamilanRepository
import com.google.firebase.database.DataSnapshot
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*


fun convertLongToTime(time: Long): String {
    val date = Date(time)
    val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    format.timeZone = TimeZone.getTimeZone("UTC")
    return format.format(date)
}

fun convertDateToLong(date: String): Long {
    val df = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    df.timeZone = TimeZone.getTimeZone("UTC")
    return df.parse(date)?.time ?: 0
}

fun convertStringToCalendar(date: String): Calendar {
    val fromDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    fromDate.timeZone = TimeZone.getTimeZone("UTC")
    val date = fromDate.parse(date)
    val cal = Calendar.getInstance(Locale.getDefault())
    cal.time = date
    return cal
}

fun List<Int>.toStringBobot(): String {
    return toString().substring(1, toString().lastIndex).replace(" ", "")
}

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

fun DataSnapshot.toMutasiHewanDomain(): MutasiHewan {
    return getValue(MutasiHewan::class.java)!!
}

fun DataSnapshot.toKehamilanDomain(): MonitoringKehamilan {
    return getValue(MonitoringKehamilan::class.java)!!
}

fun DataSnapshot.toPejantanDomain(): MonitoringPejantan {
    return getValue(MonitoringPejantan::class.java)!!
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

fun moneyFormatter(value: Long, withPrefix: Boolean = true): String {
    val myFormatter = DecimalFormat("#,###.###")
    val formatted = myFormatter.format(value.toDouble()).replace(",".toRegex(), ".")
    return if (withPrefix) "Rp$formatted" else formatted
}