package com.example.sapiii.feature.ternakku.sapi.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.sapiii.base.BaseViewModel

class SapiViewModel : BaseViewModel() {
    private val onLoadSapi = MutableLiveData<Unit>()
    val sapi = Transformations.switchMap(onLoadSapi) {
        repository.loadSapi()
    }

    fun loadSapi() {
        onLoadSapi.value = Unit
    }
}