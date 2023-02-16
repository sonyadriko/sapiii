package com.example.sapiii.feature.ternakku.sapi.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.sapiii.repository.SapiRepository

class SapiViewModel : ViewModel() {
    private val repository : SapiRepository = SapiRepository().getInstance()

    private val onLoadSapi = MutableLiveData<Unit>()
    val sapi = Transformations.switchMap(onLoadSapi) {
        repository.loadSapi()
    }

    fun loadSapi() {
        onLoadSapi.value = Unit
    }
}