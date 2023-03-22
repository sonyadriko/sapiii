package com.example.sapiii.feature.mutasi.kambing

import android.os.Bundle
import com.example.sapiii.base.BaseActivity
import com.example.sapiii.constanst.Constant
import com.example.sapiii.databinding.ActivityDetailMutasiKambingBinding
import com.example.sapiii.domain.MutasiHewan
import com.example.sapiii.util.*
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import java.util.Date

class DetailMutasiKambingActivity : BaseActivity() {

    private var isShow = false
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
        binding.btnDateKambing.setOnClickListener {
            if (isShow.not()) {
                datePicker?.show(supportFragmentManager, "dialog") ?: kotlin.run {
                    showToast("Tidak ada data")
                }
                isShow = true
            }
        }

        binding.btnUpdateDetailMutasi.setOnClickListener {
            val nama = binding.tvNamaDetailMutasiKambing.text.toString()
            val tanggal = binding.etDateKambing.text.toString()
            val keterangan = binding.spinnerTipeMutasi.selectedItem.toString()

            val mutasiKambing = MutasiHewan(nama, tanggal, keterangan)
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
                override fun onDataChange(snapshot: DataSnapshot) {
                    try {
                        if (snapshot.exists()) {
                            val mutasiSap = snapshot.toMutasiHewanDomain()
                            tvNamaDetailMutasiKambing.text = mutasiSap.nama
                            etDateKambing.setText(mutasiSap.tanggal)
                            spinnerTipeMutasi.selectedItem

                            datePicker = MaterialDatePicker.Builder.datePicker()
                                .setSelection(Date(convertDateToLong(mutasiSap.tanggal)).time)
                                .setTitleText("Pilih Tanggal")
                                .build()

                            datePicker?.addOnPositiveButtonClickListener {
                                etDateKambing.setText(convertLongToTime(it))
                            }

                            datePicker?.addOnDismissListener {
                                isShow = false
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