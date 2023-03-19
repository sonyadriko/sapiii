package com.example.sapiii.feature.reproduksi.view.pejantan

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.sapiii.base.BaseViewModel

class MonitoringPejantanViewModel : BaseViewModel() {
    private val onLoadMonitoringPejantan = MutableLiveData<Unit>()
    val monitoringpejantan = Transformations.switchMap(onLoadMonitoringPejantan) {
        monitoringPejantan.loadMonitoringPejantan()
    }

    fun loadMonitoringPejantan() {
        onLoadMonitoringPejantan.value = Unit
    }
}