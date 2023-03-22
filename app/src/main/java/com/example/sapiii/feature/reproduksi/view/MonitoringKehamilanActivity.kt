package com.example.sapiii.feature.reproduksi.view

import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.sapiii.base.BaseActivity
import com.example.sapiii.constanst.Constant
import com.example.sapiii.databinding.ActivityMonitoringKehamilanBinding
import com.example.sapiii.domain.MonitoringKehamilan
import com.example.sapiii.domain.MonitoringPejantan
import com.example.sapiii.repository.KambingRepository
import com.example.sapiii.util.convertLongToTime
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*

class MonitoringKehamilanActivity : BaseActivity() {

    private var isShow = false
    private var datePicker: MaterialDatePicker<Long>? = null

    private val repository = KambingRepository().getInstance()
    private lateinit var binding: ActivityMonitoringKehamilanBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMonitoringKehamilanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fetchNamaKodeData()

        val database = Firebase.database
        val myRef = database.getReference("Monitoring_Kehamilan")

        datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Pilih Tanggal")
            .build()

        datePicker?.addOnPositiveButtonClickListener {
            binding.etDateKehamilan.setText(convertLongToTime(it))
        }

        datePicker?.addOnDismissListener {
            isShow = false
        }

        binding.etDateKehamilan.setOnClickListener {
            if (isShow.not()) {
                datePicker?.show(supportFragmentManager, "dialog") ?: kotlin.run {
                    showToast("Tidak ada data")
                }
                isShow = true
            }
        }

        datePicker?.addOnPositiveButtonClickListener {
            binding.etPerkiraanLahir.setText(convertLongToTime(it))
        }

        datePicker?.addOnDismissListener {
            isShow = false
        }

        binding.etPerkiraanLahir.setOnClickListener {
            if (isShow.not()) {
                datePicker?.show(supportFragmentManager, "dialog") ?: kotlin.run {
                    showToast("Tidak ada data")
                }
                isShow = true
            }
        }

        binding.btnSaveMk.setOnClickListener{
            val nama = binding.spinnerkp.selectedItem.toString()
            val tanggal = binding.etDateKehamilan.text.toString()
            val tanggal2 = binding.etPerkiraanLahir.text.toString()

            val kodeKandang = binding.etKodeKandangMk.text.toString()
//


            val monitoringKehamilan = MonitoringKehamilan(nama, kodeKandang, tanggal, tanggal2)
            myRef.child(monitoringKehamilan.nama).setValue(monitoringKehamilan)
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