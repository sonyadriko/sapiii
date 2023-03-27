package com.example.sapiii.feature.kesehatan.sapi.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sapiii.base.BaseViewModel
import com.example.sapiii.domain.Kesehatan
import com.example.sapiii.domain.Sapi

class KesehatanViewModel : BaseViewModel() {
    private lateinit var dataSapi: Sapi
    private var kesehatanState = Kesehatan()

    private val _viewState = MutableLiveData<KesehatanViewState>()
    val viewState: LiveData<KesehatanViewState> get() = _viewState

    fun initDataSapi(sapi: Sapi) {
        dataSapi = sapi
        kesehatanState = dataSapi.kesehatan
    }

    fun updateDosis(vaksin: Int, b: Boolean) {
        kesehatanState = when (vaksin) {
            1 -> kesehatanState.copy(vaksinDosis1 = b)
            2 -> kesehatanState.copy(vaksinDosis2 = b)
            3 -> kesehatanState.copy(vaksinDosis3 = b)
            else -> kesehatanState
        }
    }

    fun updateKesehatan(sehat: Boolean, keterangan: String, onComplete: (isSuccess: Boolean) -> Unit) {
        _viewState.value = KesehatanViewState.LOADING
        val newSapi = dataSapi.copy(kesehatan = kesehatanState.copy(sehat = sehat, keterangan = keterangan))
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