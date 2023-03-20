package com.example.sapiii.domain

import android.os.Parcelable
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import kotlinx.android.parcel.Parcelize
import java.io.Serializable
import java.util.*

@Parcelize
class MutasiKambing (
    @SerializedName("nama")
    val nama: String = "",
//    @SerializedName("tanggal")
//    val tanggal: Date = Date(),
    @SerializedName("tanggal")
    val tanggal: String = "",
    @SerializedName("tipe")
    val tipe: String = "",
    ) : Serializable, Parcelable {
        fun toMap(): Map<String, Any?> {
            val gson = Gson()
            val json = gson.toJson(this)
            return gson.fromJson(json, object : TypeToken<Map<String, Any>>() {}.type)
        }
}