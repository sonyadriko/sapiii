package com.example.sapiii.feature.reproduksi.pejantan

import android.os.Build
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import com.example.sapiii.base.BaseActivity
import com.example.sapiii.constanst.Constant.kelaminList
import com.example.sapiii.databinding.ActivityMonitoringPejantanBinding
import com.example.sapiii.repository.KambingRepository

class MonitoringPejantanActivity : BaseActivity() {

    private val repository = KambingRepository().getInstance()
    private lateinit var binding: ActivityMonitoringPejantanBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMonitoringPejantanBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        binding.datepickerMp.setOnDateChangedListener { _, year, month, dayOfMonth ->
//            val day = binding.datepickerMp.dayOfMonth
//            val month = binding.datepickerMp.month + 1
//            val year = binding.datepickerMp.year
//
//        }

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