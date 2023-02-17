package com.example.sapiii.domain

import com.google.gson.annotations.SerializedName
import java.io.Serializable

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
    val kedatangan: KedatanganK = KedatanganK(),
    @SerializedName("data")
    val data: DataKambing = DataKambing(),
    @SerializedName("pemilik")
    val pemilik: PemilikK = PemilikK(),
) : Serializable

data class KedatanganK(
    @SerializedName("kedatangan_hewan")
    val bulan: String = "",
    @SerializedName("usia_kedatangan")
    val usia: String = "",
    @SerializedName("berat_badan_awal")
    val beratBadanAwal: String = "",
) : Serializable

data class DataKambing(
    @SerializedName("usia")
    val usia: String = "",
    @SerializedName("berat_badan")
    val berat: String = "",
    @SerializedName("status")
    val status: String = "",
) : Serializable

data class PemilikK(
    @SerializedName("nama")
    val nama: String = "",
    @SerializedName("no_telepon")
    val noTel: String = "",
    @SerializedName("alamat")
    val alamat: String = "",
) : Serializable