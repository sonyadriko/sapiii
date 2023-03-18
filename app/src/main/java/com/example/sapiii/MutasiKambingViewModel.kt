package com.example.sapiii

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.sapiii.base.BaseViewModel

class MutasiKambingViewModel : BaseViewModel() {
    private val onLoadMutasiKambing = MutableLiveData<Unit>()
    val mutasikambing = Transformations.switchMap(onLoadMutasiKambing) {
        mutasiKambingRepository.loadMutasiKambing()
    }

    fun loadMutasiSapi() {
        onLoadMutasiKambing.value = Unit
    }
}