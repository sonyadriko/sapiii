package com.example.sapiii.feature.ternakku.kambing.viewmodel

import androidx.lifecycle.ViewModel
import com.example.sapiii.constanst.Constant.kelaminList
import com.example.sapiii.constanst.Constant.statusList
import com.example.sapiii.domain.Kambing
import com.example.sapiii.repository.KambingRepository

class TambahDataKambingViewModel: ViewModel() {
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