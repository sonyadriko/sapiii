package com.example.sapiii.feature.reproduksi.view

import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.sapiii.base.BaseActivity
import com.example.sapiii.constanst.Constant.kelaminList
import com.example.sapiii.databinding.ActivityMonitoringPejantanBinding
import com.example.sapiii.repository.KambingRepository

class MonitoringPejantanActivity : BaseActivity() {

    private val repository = KambingRepository().getInstance()
    private lateinit var binding: ActivityMonitoringPejantanBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMonitoringPejantanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fetchNamaKodeData()
    }

    private fun fetchNamaKodeData() = with(binding) {
        showProgressDialog()
        repository.loadKambing().observe(this@MonitoringPejantanActivity) { listKambing ->
            dismissProgressDialog()
            if (listKambing.isNotEmpty()) {
                val listOfNames: Array<String> = listKambing.filter {
                    it.kelamin == kelaminList[0] // indicate pejantan
                }.map { it.tag }.toTypedArray()
                val spinnerArrayAdapter =
                    ArrayAdapter(
                        this@MonitoringPejantanActivity,
                        android.R.layout.simple_spinner_dropdown_item,
                        listOfNames
                    )
                spinnerkp.adapter = spinnerArrayAdapter
            } else {
                showToast("Data Kosong")
            }
        }
    }
}