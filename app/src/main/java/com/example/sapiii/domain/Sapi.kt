package com.example.sapiii.domain

import android.os.Parcelable
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
data class Sapi(
    @SerializedName("tag")
    val tag: String = "",
    @SerializedName("jenis")
    val jenis: String = "",
    @SerializedName("kelamin")
    val kelamin: String = "",
    @SerializedName("asal")
    val asal: String = "",
    @SerializedName("kedatangan")
    val kedatangan: Kedatangan = Kedatangan(),
    @SerializedName("data")
    val data: DataSapi = DataSapi(),
    @SerializedName("kesehatan")
    val kesehatan: Kesehatan = Kesehatan(),
    @SerializedName("pemilik")
    val pemilik: Pemilik = Pemilik(),
) : Serializable, Parcelable {
    fun toMap(): Map<String, Any?> {
        val gson = Gson()
        val json = gson.toJson(this)
        return gson.fromJson(json, object : TypeToken<Map<String, Any>>() {}.type)
    }
}

@Parcelize
data class Kesehatan(
    @SerializedName("sehat")
    val sehat: Boolean = false,
    @SerializedName("vaksinDosis")
    val vaksinDosis: Int = -1 // -1 berarti belum vaksin sama sekali
) : Serializable, Parcelable

@Parcelize
data class Kedatangan(
    @SerializedName("kedatanganHewan")
    val bulan: String = "",
    @SerializedName("usiaKedatangan")
    val usia: String = "",
    @SerializedName("beratBadanAwal")
    val beratBadanAwal: String = "",
) : Serializable, Parcelable

@Parcelize
data class DataSapi(
    @SerializedName("usia")
    val usia: String = "",
    @SerializedName("beratBadan")
    val berat: String = "",
    @SerializedName("status")
    val status: String = "",
) : Serializable, Parcelable

@Parcelize
data class Pemilik(
    @SerializedName("nama")
    val nama: String = "",
    @SerializedName("noTelepon")
    val noTelepon: String = "",
    @SerializedName("alamat")
    val alamat: String = "",
) : Serializable, Parcelable