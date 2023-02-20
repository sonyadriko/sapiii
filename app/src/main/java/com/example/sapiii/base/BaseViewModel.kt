package com.example.sapiii.base

import androidx.lifecycle.ViewModel
import com.example.sapiii.repository.SapiRepository

abstract class BaseViewModel: ViewModel() {
    val repository : SapiRepository = SapiRepository().getInstance()
}