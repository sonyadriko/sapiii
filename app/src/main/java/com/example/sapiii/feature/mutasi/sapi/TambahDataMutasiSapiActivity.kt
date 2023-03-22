package com.example.sapiii.feature.mutasi.sapi

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import com.example.sapiii.R
import com.example.sapiii.base.BaseActivity
import com.example.sapiii.databinding.ActivityTambahDataMutasiSapiBinding
import com.example.sapiii.domain.MutasiHewan
import com.example.sapiii.repository.SapiRepository
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*

class TambahDataMutasiSapiActivity : BaseActivity() {

    private lateinit var binding: ActivityTambahDataMutasiSapiBinding

    private val repository = SapiRepository().getInstance()
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTambahDataMutasiSapiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val database = Firebase.database
        val myRef = database.getReference("Mutasi_Sapi")
        val dateEditText = findViewById<EditText>(R.id.et_date_sapi)
        val timestamp = System.currentTimeMillis() // contoh timestamp

// Set text pada EditText dengan format tanggal yang diinginkan
//        dateEditText.setText(formatDate(timestamp))

        fetchNamaKodeData()

        binding.btnSubmitMutasiSapi.setOnClickListener{
            val nama = binding.spinnermutasisapi.selectedItem.toString()
//            val dateString = binding.etDateSapi.text.toString()
//            val dateFormat = SimpleDateFormat("dd/MM/yyyy")
//            val tanggal: Date = dateFormat.parse(dateString)
            val tanggal = binding.etDateSapi.text.toString()
            val tipe = binding.spinnerTipeMutasiSapi.selectedItem.toString()

            val mutasiHewan = MutasiHewan(nama, tanggal, tipe)
            myRef.child(mutasiHewan.nama).setValue(mutasiHewan)
                .addOnSuccessListener {
                    showToast("Mutasi Sapi berhasil disimpan")
                    finish()
                }
                .addOnFailureListener {
                    showToast("Mutasi Sapi gagal disimpan")
                }
        }

    }

    private fun formatDate(timestamp: Long): String {
        return dateFormat.format(Date(timestamp))
    }

    private fun fetchNamaKodeData() = with(binding) {
        showProgressDialog()
        repository.loadSapi().observe(this@TambahDataMutasiSapiActivity) { listSapi ->
            dismissProgressDialog()
            if (listSapi.isNotEmpty()) {
                val listOfNames: Array<String> = listSapi.map { it.tag }.toTypedArray()
                val spinnerArrayAdapter =
                    ArrayAdapter(
                        this@TambahDataMutasiSapiActivity,
                        android.R.layout.simple_spinner_dropdown_item,
                        listOfNames
                    )
                spinnermutasisapi.adapter = spinnerArrayAdapter
            } else {
                showToast("Data Kosong")
            }
        }
    }

    private fun initListener() = with(binding){
//        btnSubmitMutasiSapi.setOnClickListener{
//            val nama = spinnermutasisapi.selectedItem.toString()
//            val dateString = etDateSapi.text.toString()
//            val dateFormat = SimpleDateFormat("dd/MM/yyyy")
//            val tanggal: Date = dateFormat.parse(dateString)
//            val tipe = spinnerTipeMutasiSapi.selectedItem.toString()
//
//            val mutasiSapi = MutasiSapi(nama, tanggal, tipe)
//            myRef.child(mutasiSapi.nama).setValue(mutasiSapi)
//                .addOnSuccessListener {
//                    showToast("Artikel berhasil disimpan")
//                    finish()
//                }
//                .addOnFailureListener {
//                    showToast("Artikel gagal disimpan")
//                }
//        }
    }
}