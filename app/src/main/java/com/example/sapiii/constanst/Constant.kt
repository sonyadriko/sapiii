package com.example.sapiii.constanst

object Constant {
    enum class Role {
        PETERNAK,
        INVESTOR,
        ADMIN
    }

    // database reference
    const val REFERENCE_SAPI = "Sapi"
    const val REFERENCE_KAMBING = "Kambing"
    const val REFERENCE_USER = "Users"
    const val REFERENCE_ARTIKEL = "Artikel"
    const val REFERENCE_INVES = "Inves"

    // DEEP LINK REGION ================
    const val DEEP_LINK_ROOT = "https://sapiii.app"

    // QUERY PARAMETER
    const val NAMA_SAPI_QUERY_PARAM = "namaSapi"
    const val NAMA_KAMBING_QUERY_PARAM = "namaKambing"

    // END REGION =====================

    val statusList = listOf("Siap Jual", "Belum Siap Jual")
}