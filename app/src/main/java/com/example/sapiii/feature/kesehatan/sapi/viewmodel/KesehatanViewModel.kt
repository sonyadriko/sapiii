package com.example.sapiii.feature.kesehatan.sapi.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sapiii.base.BaseViewModel
import com.example.sapiii.domain.Kesehatan
import com.example.sapiii.domain.Sapi

class KesehatanViewModel: BaseViewModel() {
    private lateinit var dataSapi: Sapi
    private var dosis: Int = -1

    private val _viewState = MutableLiveData<KesehatanViewState>()
    val viewState: LiveData<KesehatanViewState> get() = _viewState

    fun initDataSapi(sapi: Sapi) {
        dataSapi = sapi
    }

    fun updateDosis(dosis: Int) {
        this.dosis = dosis
    }

    fun updateKesehatan(sehat: Boolean, onComplete: (isSuccess: Boolean) -> Unit) {
        _viewState.value = KesehatanViewState.LOADING
        val newSapi = dataSapi.copy(kesehatan = Kesehatan(
            sehat = sehat,
            vaksinDosis = dosis
        ))
        sapiRepository.updateSapi(newSapi) { isSuccess ->
            _viewState.value = KesehatanViewState.INITIAL
            onComplete(isSuccess)
        }
    }
}

enum class KesehatanViewState {
    LOADING,
    INITIAL
}