package com.example.sapiii.feature.kesehatan.kambing.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sapiii.base.BaseViewModel
import com.example.sapiii.domain.Kambing
import com.example.sapiii.domain.Kesehatan

class KesehatanKambingViewModel : BaseViewModel() {
    private lateinit var dataKambing: Kambing
    private var dosis: Int = -1

    private val _viewState = MutableLiveData<KesehatanKambingViewState>()
    val viewState: LiveData<KesehatanKambingViewState> get() = _viewState

    fun initDataKambing(kambing: Kambing) {
        dataKambing = kambing
    }

    fun updateDosis(dosis: Int) {
        this.dosis = dosis
    }

    fun updateKesehatan(sehat: Boolean, onComplete: (isSuccess: Boolean) -> Unit) {
        _viewState.value = KesehatanKambingViewState.LOADING
        val newKambing = dataKambing.copy(
            kesehatan = Kesehatan(
                sehat = sehat,
                vaksinDosis = dosis
            )
        )
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