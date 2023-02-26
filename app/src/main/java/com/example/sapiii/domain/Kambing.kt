package com.example.sapiii.domain

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
data class Kambing(
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
    val data: DataHewan = DataHewan(),
    @SerializedName("pemilik")
    val pemilik: Pemilik = Pemilik(),
) : Serializable, Parcelable {
    fun toMap(): Map<String, Any?> {
        val gson = Gson()
        val json = gson.toJson(this)
        return gson.fromJson(json, object : TypeToken<Map<String, Any>>() {}.type)
    }
}