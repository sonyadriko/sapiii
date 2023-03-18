package com.example.sapiii.feature.reproduksi.view

import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.sapiii.base.BaseActivity
import com.example.sapiii.constanst.Constant
import com.example.sapiii.databinding.ActivityMonitoringKehamilanBinding
import com.example.sapiii.repository.KambingRepository

class MonitoringKehamilanActivity : BaseActivity() {

    private val repository = KambingRepository().getInstance()
    private lateinit var binding: ActivityMonitoringKehamilanBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMonitoringKehamilanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fetchNamaKodeData()
    }

    private fun fetchNamaKodeData() = with(binding) {
        showProgressDialog()
        repository.loadKambing().observe(this@MonitoringKehamilanActivity) { listKambing ->
            dismissProgressDialog()
            if (listKambing.isNotEmpty()) {
                val listOfNames: Array<String> = listKambing.filter {
                    it.kelamin == Constant.kelaminList[1] // indicate betine
                }.map { it.tag }.toTypedArray()
                val spinnerArrayAdapter =
                    ArrayAdapter(
                        this@MonitoringKehamilanActivity,
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