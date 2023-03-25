package com.example.sapiii.feature.reproduksi.view.kehamilan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sapiii.base.BaseActivity
import com.example.sapiii.constanst.Constant
import com.example.sapiii.databinding.ActivityEditKehamilanBinding
import com.example.sapiii.domain.MonitoringKehamilan
import com.example.sapiii.domain.MutasiHewan
import com.example.sapiii.util.*
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import java.util.*

class EditKehamilanActivity : BaseActivity() {

    private var isShow = false
    private var datePicker: MaterialDatePicker<Long>? = null
    private var datePicker2: MaterialDatePicker<Long>? = null


    private lateinit var binding: ActivityEditKehamilanBinding
    private lateinit var KehamilanRef: DatabaseReference
    private lateinit var namaH: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditKehamilanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        KehamilanRef = database.getReference(Constant.REFERENCE_KEHAMILAN)
        namaH = intent.getStringExtra("nama") ?: ""

        getDetailKehamilan()
        initListener()
    }

    private fun initListener() {
        binding.btnDateKehamilan.setOnClickListener {
            if (isShow.not()) {
                datePicker?.show(supportFragmentManager, "dialog") ?: kotlin.run {
                    showToast("Tidak ada data")
                }
                isShow = true
            }
        }

        binding.btnDatePerkiraan.setOnClickListener {
            if (isShow.not()) {
                datePicker2?.show(supportFragmentManager, "dialog") ?: kotlin.run {
                    showToast("Tidak ada data")
                }
                isShow = true
            }
        }


        binding.btnUpdateDetailKehamilan.setOnClickListener {
            val nama = binding.tvNamaDetailMutasiKambing.text.toString()
            val kandang = binding.etKodeKandangMk.text.toString()
            val tanggal = binding.etDateKehamilan.text.toString()
            val perkiraan = binding.etDatePerkiraan.text.toString()

            val monitoringKehamilan = MonitoringKehamilan(nama, kandang, tanggal, perkiraan)
            KehamilanRef.child(monitoringKehamilan.nama).setValue(monitoringKehamilan)
                .addOnSuccessListener {
                    showToast("Data berhasil diupdate")
                    finish()
                }
                .addOnFailureListener {
                    showToast("Data gagal diupdate")
                }
        }

        binding.btnDeleteDetailMutasi.setOnClickListener {

            KehamilanRef.child(namaH).removeValue()
            finish()

        }
    }


    private fun getDetailKehamilan() = with(binding){
        showProgressDialog()
        KehamilanRef.child(namaH)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    try {
                        if (snapshot.exists()) {
                            val kehamilanData = snapshot.toKehamilanDomain()
                            tvNamaDetailMutasiKambing.text = kehamilanData.nama
                            etKodeKandangMk.setText(kehamilanData.kandang)
                            etDateKehamilan.setText(kehamilanData.tanggalAwal)
                            etDatePerkiraan.setText(kehamilanData.tanggalPerkiraan)

                            datePicker = MaterialDatePicker.Builder.datePicker()
                                .setSelection(Date(convertDateToLong(kehamilanData.tanggalAwal)).time)
                                .setTitleText("Pilih Tanggal")
                                .build()

                            datePicker?.addOnPositiveButtonClickListener {
                                etDateKehamilan.setText(convertLongToTime(it))
                            }

                            datePicker?.addOnDismissListener {
                                isShow = false
                            }

                            datePicker2 = MaterialDatePicker.Builder.datePicker()
                                .setSelection(Date(convertDateToLong(kehamilanData.tanggalAwal)).time)
                                .setTitleText("Pilih Tanggal")
                                .build()

                            datePicker2?.addOnPositiveButtonClickListener {
                                etDatePerkiraan.setText(convertLongToTime(it))
                            }

                            datePicker2?.addOnDismissListener {
                                isShow = false
                            }
                        } else throw Exception("Data is not found")
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