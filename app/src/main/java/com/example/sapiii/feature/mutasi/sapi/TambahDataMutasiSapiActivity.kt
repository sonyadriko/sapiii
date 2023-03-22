package com.example.sapiii.feature.mutasi.sapi

import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.sapiii.base.BaseActivity
import com.example.sapiii.databinding.ActivityTambahDataMutasiSapiBinding
import com.example.sapiii.domain.MutasiHewan
import com.example.sapiii.repository.SapiRepository
import com.example.sapiii.util.convertLongToTime
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class TambahDataMutasiSapiActivity : BaseActivity() {

    private var isShow = false
    private var datePicker: MaterialDatePicker<Long>? = null

    private lateinit var binding: ActivityTambahDataMutasiSapiBinding

    private val repository = SapiRepository().getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTambahDataMutasiSapiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val database = Firebase.database
        val myRef = database.getReference("Mutasi_Sapi")

        fetchNamaKodeData()
        datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Pilih Tanggal")
            .build()

        datePicker?.addOnPositiveButtonClickListener {
            binding.etDateSapi.setText(convertLongToTime(it))
        }

        datePicker?.addOnDismissListener {
            isShow = false
        }

        binding.btnDateSapi.setOnClickListener {
            if (isShow.not()) {
                datePicker?.show(supportFragmentManager, "dialog") ?: kotlin.run {
                    showToast("Tidak ada data")
                }
                isShow = true
            }
        }

        binding.btnSubmitMutasiSapi.setOnClickListener {
            val nama = binding.spinnermutasisapi.selectedItem.toString()
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
}