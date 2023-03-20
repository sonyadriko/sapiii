package com.example.sapiii.feature.ternakku.sapi.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.sapiii.base.BaseViewModel
import com.example.sapiii.domain.Sapi

class SapiViewModel : BaseViewModel() {
    private val onLoadSapi = MutableLiveData<Unit>()
    val sapi = Transformations.switchMap(onLoadSapi) {
        sapiRepository.loadSapi()
    }


    fun loadSapi() {
        onLoadSapi.value = Unit
    }
}