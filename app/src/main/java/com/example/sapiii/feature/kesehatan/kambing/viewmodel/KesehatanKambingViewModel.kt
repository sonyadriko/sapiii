package com.example.sapiii.feature.kesehatan.kambing.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sapiii.base.BaseViewModel
import com.example.sapiii.domain.Kambing
import com.example.sapiii.domain.Kesehatan

class KesehatanKambingViewModel : BaseViewModel() {
    private lateinit var dataKambing: Kambing
    private var kesehatanState = Kesehatan()

    private val _viewState = MutableLiveData<KesehatanKambingViewState>()
    val viewState: LiveData<KesehatanKambingViewState> get() = _viewState

    fun initDataKambing(kambing: Kambing) {
        dataKambing = kambing
        kesehatanState = dataKambing.kesehatan
    }

    fun updateDosis(vaksin: Int, b: Boolean) {
        kesehatanState = when (vaksin) {
            1 -> kesehatanState.copy(vaksinDosis1 = b)
            2 -> kesehatanState.copy(vaksinDosis2 = b)
            3 -> kesehatanState.copy(vaksinDosis3 = b)
            else -> kesehatanState
        }
    }

    fun updateKesehatan(sehat: Boolean, onComplete: (isSuccess: Boolean) -> Unit) {
        _viewState.value = KesehatanKambingViewState.LOADING
        val newKambing = dataKambing.copy(kesehatan = kesehatanState.copy(sehat = sehat))
        kambingRepository.updateKambing(newKambing) { isSuccess ->
            _viewState.value = KesehatanKambingViewState.INITIAL
            onComplete(isSuccess)
        }


    }
}

enum class KesehatanKambingViewState {
    LOADING,
    INITIAL
}