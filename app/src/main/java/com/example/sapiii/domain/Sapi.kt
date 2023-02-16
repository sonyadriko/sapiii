package com.example.sapiii.domain

import com.google.gson.annotations.SerializedName
import java.io.Serializable

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
    @SerializedName("pemilik")
    val pemilik: Pemilik = Pemilik(),
) : Serializable

data class Kedatangan(
    @SerializedName("kedatangan_hewan")
    val bulan: String = "",
    @SerializedName("usia_kedatangan")
    val usia: String = "",
    @SerializedName("berat_badan_awal")
    val beratBadanAwal: String = "",
) : Serializable

data class DataSapi(
    @SerializedName("usia")
    val usia: String = "",
    @SerializedName("berat_badan")
    val berat: String = "",
    @SerializedName("status")
    val status: String = "",
) : Serializable

data class Pemilik(
    @SerializedName("nama")
    val nama: String = "",
    @SerializedName("no_telepon")
    val noTel: String = "",
    @SerializedName("alamat")
    val alamat: String = "",
) : Serializable