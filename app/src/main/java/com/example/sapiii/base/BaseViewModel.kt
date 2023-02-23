package com.example.sapiii.base

import androidx.lifecycle.ViewModel
import com.example.sapiii.repository.ArtikelRepository
import com.example.sapiii.repository.SapiRepository

abstract class BaseViewModel: ViewModel() {
    val sapiRepository: SapiRepository = SapiRepository().getInstance()
    val artikelRepository : ArtikelRepository = ArtikelRepository.getInstance()
}