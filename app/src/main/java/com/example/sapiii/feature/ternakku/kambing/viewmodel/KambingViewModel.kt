package com.example.sapiii.feature.ternakku.kambing.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.sapiii.base.BaseViewModel

class KambingViewModel: BaseViewModel() {
    private val onLoadKambing = MutableLiveData<Unit>()
    val kambing = Transformations.switchMap(onLoadKambing) {
        kambingRepository.loadKambing()
    }

    fun loadKambing() {
        onLoadKambing.value = Unit
    }
}