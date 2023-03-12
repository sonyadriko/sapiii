package com.example.sapiii.feature.ternakku.sapi.viewmodel

import androidx.lifecycle.ViewModel
import com.example.sapiii.constanst.Constant.kelaminList
import com.example.sapiii.constanst.Constant.statusList
import com.example.sapiii.domain.Sapi
import com.example.sapiii.repository.SapiRepository

class TambahDataSapiViewModel: ViewModel() {
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