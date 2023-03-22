package com.example.sapiii.feature.mutasi.kambing

import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.sapiii.base.BaseActivity
import com.example.sapiii.databinding.ActivityTambahDataMutasiBinding
import com.example.sapiii.domain.MutasiHewan
import com.example.sapiii.repository.KambingRepository
import com.example.sapiii.util.convertLongToTime
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class TambahDataMutasiActivity : BaseActivity()  {

    private var isShow = false
    private var datePicker: MaterialDatePicker<Long>? = null

    private val repository = KambingRepository().getInstance()

    private lateinit var binding: ActivityTambahDataMutasiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTambahDataMutasiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val database = Firebase.database
        val myRef = database.getReference("Mutasi_Kambing")

        fetchNamaKodeData()
        datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Pilih Tanggal")
            .build()

        datePicker?.addOnPositiveButtonClickListener {
            binding.etDateKambing.setText(convertLongToTime(it))
        }

        datePicker?.addOnDismissListener {
            isShow = false
        }

        binding.btnDateKambing.setOnClickListener {
            if (isShow.not()) {
                datePicker?.show(supportFragmentManager, "dialog") ?: kotlin.run {
                    showToast("Tidak ada data")
                }
                isShow = true
            }
        }

        binding.btnSubmitMutasiKambing.setOnClickListener{
            val nama = binding.spinnermutasi.selectedItem.toString()
            val tanggal = binding.etDateKambing.text.toString()

            val tipe = binding.spinnerTipeMutasi.selectedItem.toString()

            val mutasiKambing = MutasiHewan(nama, tanggal, tipe)
            myRef.child(mutasiKambing.nama).setValue(mutasiKambing)
                .addOnSuccessListener {
                    showToast("Mutasi Kambing berhasil disimpan")
                    finish()
                }
                .addOnFailureListener {
                    showToast("Mutasi Kambing disimpan")
                }
        }

    }

    private fun fetchNamaKodeData() = with(binding) {
        showProgressDialog()
        repository.loadKambing().observe(this@TambahDataMutasiActivity) { listKambing ->
            dismissProgressDialog()
            if (listKambing.isNotEmpty()) {
                val listOfNames: Array<String> = listKambing.map { it.tag }.toTypedArray()
                val spinnerArrayAdapter =
                    ArrayAdapter(
                        this@TambahDataMutasiActivity,
                        android.R.layout.simple_spinner_dropdown_item,
                        listOfNames
                    )
                spinnermutasi.adapter = spinnerArrayAdapter
            } else {
                showToast("Data Kosong")
            }
        }
    }
}