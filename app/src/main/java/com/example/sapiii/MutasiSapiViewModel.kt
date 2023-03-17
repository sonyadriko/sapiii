package com.example.sapiii

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.sapiii.base.BaseViewModel

class MutasiSapiViewModel  : BaseViewModel(){
    private val onLoadMutasiSapi = MutableLiveData<Unit>()
    val mutasisapi = Transformations.switchMap(onLoadMutasiSapi) {
        mutasiSapiRepository.loadMutasiSapi()
    }

    fun loadMutasiSapi() {
        onLoadMutasiSapi.value = Unit
    }
}