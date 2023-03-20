package com.example.sapiii.feature.mutasi.sapi

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.sapiii.base.BaseViewModel
import com.example.sapiii.feature.detail.viewmodel.DetailViewModel

class MutasiSapiViewModel : BaseViewModel() {
    private val onLoadMutasiSapi = MutableLiveData<Unit>()
    val mutasisapi = Transformations.switchMap(onLoadMutasiSapi) {
        mutasiSapiRepository.loadMutasiSapi()
    }

    fun loadMutasiSapi() {
        onLoadMutasiSapi.value = Unit
    }



}