package com.example.sapiii.feature.tips.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.sapiii.base.BaseViewModel

class ArtikelViewModel : BaseViewModel() {
    private val onLoadArtikel = MutableLiveData<Unit>()
    val artikel = Transformations.switchMap(onLoadArtikel) {
        artikelRepository.loadArtikel()
    }

    fun loadArtikel() {
        onLoadArtikel.value = Unit
    }
}