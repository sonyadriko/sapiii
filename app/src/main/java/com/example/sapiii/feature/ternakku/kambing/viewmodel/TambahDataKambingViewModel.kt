package com.example.sapiii.feature.ternakku.kambing.viewmodel

import androidx.lifecycle.ViewModel
import com.example.sapiii.domain.Kambing
import com.example.sapiii.domain.Sapi
import com.example.sapiii.repository.KambingRepository
import com.example.sapiii.repository.SapiRepository

class TambahDataKambingViewModel: ViewModel() {
    val statusList = listOf("Siap Jual", "Belum Siap Jual")
    val kelaminList = listOf("Jantan", "Betina")

    var status: String = ""
        private set
    var kelamin: String = ""
        private set

    private val repository : KambingRepository = KambingRepository().getInstance()

    fun addData(kambing: Kambing, onComplete: (isSuccess: Boolean) -> Unit) {
        if (status.isEmpty() && kelamin.isEmpty()) {
            onComplete(false)
            return
        }

        repository.addKambing(kambing) {
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