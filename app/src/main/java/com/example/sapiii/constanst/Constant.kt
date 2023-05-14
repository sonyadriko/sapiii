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
    const val REFERENCE_MUTASI_SAPI = "Mutasi_Sapi"
    const val REFERENCE_MUTASI_KAMBING = "Mutasi_Kambing"
    const val REFERENCE_PEJANTAN = "Monitoring_Pejantan"
    const val REFERENCE_KEHAMILAN = "Monitoring_Kehamilan"
    const val REFERENCE_BERAT_SAPI = "Berat_Sapi"
    const val REFERENCE_BERAT_KAMBING = "Berat_Kambing"




    const val REFERENCE_INVES = "Inves"

    // DEEP LINK REGION ================
    const val DEEP_LINK_ROOT = "https://sapiii.app"

    // QUERY PARAMETER
    const val NAMA_SAPI_QUERY_PARAM = "namaSapi"
    const val NAMA_KAMBING_QUERY_PARAM = "namaKambing"

    // END REGION =====================

    const val STATUS_SIAP_JUAL = "Siap Jual"
    const val STATUS_BELUM_SIAP_JUAL = "Belum Siap Jual"
    val statusList = listOf(STATUS_SIAP_JUAL, STATUS_BELUM_SIAP_JUAL)
    val kelaminList = listOf("Jantan", "Betina")
}