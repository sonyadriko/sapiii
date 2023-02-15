package com.example.sapiii

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sapiii.Repository.SapiRepository
import com.example.sapiii.domain.Sapi

class SapiViewModel : ViewModel() {

    private val repository : SapiRepository
    private val _allSapis =  MutableLiveData<List<Sapi>>()
    val allUsers : LiveData<List<Sapi>> = _allSapis

    init {

        repository = SapiRepository().getInstance()
        repository.loadSapis(_allSapis)

    }


}