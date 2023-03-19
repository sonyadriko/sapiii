package com.example.sapiii.mutasi.kambing

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import com.example.sapiii.R
import com.example.sapiii.base.BaseActivity
import com.example.sapiii.databinding.ActivityTambahDataMutasiBinding
import com.example.sapiii.domain.MutasiKambing
import com.example.sapiii.repository.KambingRepository
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*

class TambahDataMutasiActivity : BaseActivity()  {

    private val repository = KambingRepository().getInstance()
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    private lateinit var binding: ActivityTambahDataMutasiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTambahDataMutasiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val database = Firebase.database
        val myRef = database.getReference("Mutasi_Kambing")
        val dateEditText = findViewById<EditText>(R.id.et_date_kambing)
        val timestamp = System.currentTimeMillis() // contoh timestamp

// Set text pada EditText dengan format tanggal yang diinginkan
//        dateEditText.setText(formatDate(timestamp))

        fetchNamaKodeData()

        binding.btnSubmitMutasiKambing.setOnClickListener{
            val nama = binding.spinnermutasi.selectedItem.toString()
            val dateString = binding.etDateKambing.text.toString()
            val dateFormat = SimpleDateFormat("dd/MM/yyyy")
            val tanggal: Date = dateFormat.parse(dateString)
            val tipe = binding.spinnerTipeMutasi.selectedItem.toString()

            val mutasiKambing = MutasiKambing(nama, tanggal, tipe)
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

    private fun formatDate(timestamp: Long): String {
        return dateFormat.format(Date(timestamp))
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