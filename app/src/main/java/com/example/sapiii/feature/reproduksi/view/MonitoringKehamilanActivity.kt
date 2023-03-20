package com.example.sapiii.feature.reproduksi.view

import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.sapiii.base.BaseActivity
import com.example.sapiii.constanst.Constant
import com.example.sapiii.databinding.ActivityMonitoringKehamilanBinding
import com.example.sapiii.domain.MonitoringKehamilan
import com.example.sapiii.domain.MonitoringPejantan
import com.example.sapiii.repository.KambingRepository
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*

class MonitoringKehamilanActivity : BaseActivity() {

    private val repository = KambingRepository().getInstance()
    private lateinit var binding: ActivityMonitoringKehamilanBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMonitoringKehamilanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fetchNamaKodeData()

        val database = Firebase.database
        val myRef = database.getReference("Monitoring_Kehamilan")

        binding.btnSaveMk.setOnClickListener{
            val nama = binding.spinnerkp.selectedItem.toString()
            val tanggal = binding.etDateKehamilan.text.toString()
            val tanggal2 = binding.etPerkiraanLahir.text.toString()
//            val dateString = binding.etDateKehamilan.text.toString()
//            val dateFormat = SimpleDateFormat("dd/MM/yyyy")
//            val tanggal: Date = dateFormat.parse(dateString)
//            val dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy")
//            val tanggal: LocalDate = LocalDate.parse(dateString, dateFormat)
            val kodeKandang = binding.etKodeKandangMk.text.toString()
//            val calendar = Calendar.getInstance()
//            calendar.time = tanggal
//            calendar.add(Calendar.MONTH, 9)
//            val tanggal2 = calendar.time // Mengonversi ke objek Date


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