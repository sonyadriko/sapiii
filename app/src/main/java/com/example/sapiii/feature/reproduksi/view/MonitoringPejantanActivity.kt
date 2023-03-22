package com.example.sapiii.feature.reproduksi.view

import android.os.Build
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import com.example.sapiii.base.BaseActivity
import com.example.sapiii.constanst.Constant.kelaminList
import com.example.sapiii.databinding.ActivityMonitoringPejantanBinding
import com.example.sapiii.domain.MonitoringPejantan
import com.example.sapiii.repository.KambingRepository
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*

class MonitoringPejantanActivity : BaseActivity() {

    private val repository = KambingRepository().getInstance()
    private lateinit var binding: ActivityMonitoringPejantanBinding

    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMonitoringPejantanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val database = Firebase.database
        val myRef = database.getReference("Monitoring_Pejantan")

//        binding.datepickerMp.setOnDateChangedListener { _, year, month, dayOfMonth ->
//            val day = binding.datepickerMp.dayOfMonth
//            val month = binding.datepickerMp.month + 1
//            val year = binding.datepickerMp.year
//
//        }

        fetchNamaKodeData()

        binding.btnSaveMp.setOnClickListener{
            val nama = binding.spinnerkp.selectedItem.toString()
            val dateString = binding.etDatePejantan.text.toString()
            val dateFormat = SimpleDateFormat("dd/MM/yyyy")
            val tanggal: Date = dateFormat.parse(dateString)
            val kodeKandang = binding.etKodeKandang.text.toString()

            val monitoringPejantan = MonitoringPejantan(nama, kodeKandang, tanggal)
            myRef.child(monitoringPejantan.nama).setValue(monitoringPejantan)
                .addOnSuccessListener {
                    showToast("Data berhasil disimpan")
                    finish()
                }
                .addOnFailureListener {
                    showToast("Data gagal disimpan")
                }
        }
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