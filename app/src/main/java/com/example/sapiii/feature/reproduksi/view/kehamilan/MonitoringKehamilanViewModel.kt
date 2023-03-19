package com.example.sapiii.feature.reproduksi.view.kehamilan

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.sapiii.base.BaseViewModel

class MonitoringKehamilanViewModel : BaseViewModel() {
    private val onLoadMonitoringKehamilan = MutableLiveData<Unit>()
    val monitoringkehamilan = Transformations.switchMap(onLoadMonitoringKehamilan) {
        monitoringKehamilan.loadMonitoringKehamilan()
    }

    fun loadMonitoringKehamilan() {
        onLoadMonitoringKehamilan.value = Unit
    }
}