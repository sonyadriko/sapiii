package com.example.sapiii

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.example.sapiii.base.BaseActivity
import com.example.sapiii.constanst.Constant
import com.example.sapiii.databinding.ActivityDetailMutasiBinding
import com.example.sapiii.databinding.ActivityDetailMutasiKambingBinding
import com.example.sapiii.domain.MutasiKambing
import com.example.sapiii.util.*
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class DetailMutasiKambingActivity : BaseActivity() {

    private var datePicker: MaterialDatePicker<Long>? = null

    private lateinit var binding: ActivityDetailMutasiKambingBinding
    private lateinit var MutasiRef: DatabaseReference
    private lateinit var namaH: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMutasiKambingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        MutasiRef = database.getReference(Constant.REFERENCE_MUTASI_KAMBING)
        namaH = intent.getStringExtra("nama") ?: ""

        getDetailMutasi()
        initListener()
    }

    private fun initListener() {
        binding.btnDateSapi.setOnClickListener {
            datePicker?.show(supportFragmentManager, "dialog") ?: kotlin.run {
                showToast("Tidak ada data")
            }
        }

        binding.btnUpdateDetailMutasi.setOnClickListener {
            val nama = binding.tvNamaDetailMutasiKambing.text.toString()
            val tanggal = binding.etDateSapi.text.toString()
            val keterangan = binding.spinnerTipeMutasi.selectedItem.toString()

            val mutasiKambing = MutasiKambing(nama, tanggal, keterangan)
            MutasiRef.child(mutasiKambing.nama).setValue(mutasiKambing)
                .addOnSuccessListener {
                    showToast("Mutasi Kambing berhasil diupdate")
                    finish()
                }
                .addOnFailureListener {
                    showToast("Mutasi Kambing gagal diupdate")
                }
        }

        binding.btnDeleteDetailMutasi.setOnClickListener {

            MutasiRef.child(namaH).removeValue()
            finish()

        }
    }

    private fun getDetailMutasi() = with(binding) {
        showProgressDialog()
        MutasiRef.child(namaH)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                @RequiresApi(Build.VERSION_CODES.O)
                override fun onDataChange(snapshot: DataSnapshot) {
                    try {
                        if (snapshot.exists()) {
                            val mutasiSap = snapshot.toMutasiKambingDomain()
                            tvNamaDetailMutasiKambing.text = mutasiSap.nama
                            etDateSapi.setText(mutasiSap.tanggal)
                            spinnerTipeMutasi.selectedItem

                            datePicker = MaterialDatePicker.Builder.datePicker()
                                .setSelection(Date(convertDateToLong(mutasiSap.tanggal)).time)
                                .setTitleText("Pilih Tanggal")
                                .build()

                            datePicker?.addOnPositiveButtonClickListener {
                                binding.etDateSapi.setText(convertLongToTime(it))
                            }
                        } else throw Exception("Mutasi Kambing is not found")
                    } catch (e: Exception) {
                        showToast("error")
                    }

                    dismissProgressDialog()
                }

                override fun onCancelled(error: DatabaseError) {
                    dismissProgressDialog()
                    showToast("cancelled, ${error.message}")
                }
            })
    }
}