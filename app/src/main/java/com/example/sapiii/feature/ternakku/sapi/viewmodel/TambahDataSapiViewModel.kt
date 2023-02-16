package com.example.sapiii.feature.ternakku.sapi.viewmodel

import androidx.lifecycle.ViewModel
import com.example.sapiii.domain.Sapi
import com.example.sapiii.repository.SapiRepository

class TambahDataSapiViewModel: ViewModel() {
    val statusList = listOf("Siap Jual", "Belum Siap Jual")
    val kelaminList = listOf("Jantan", "Betina")

    var status: String = ""
        private set
    var kelamin: String = ""
        private set

    private val repository : SapiRepository = SapiRepository().getInstance()

    fun addData(sapi: Sapi, onComplete: (isSuccess: Boolean) -> Unit) {
        if (status.isEmpty() && kelamin.isEmpty()) {
            onComplete(false)
            return
        }

        repository.addSapi(sapi) {
            onComplete(it)
        }
    }

    fun setStatus(position: Int) {
        status = statusList[position]
    }

    fun setKelamin(position: Int) {
        kelamin = kelaminList[position]
    }
}