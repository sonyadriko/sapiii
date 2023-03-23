package com.example.sapiii

import android.os.Bundle
import com.example.sapiii.base.BaseActivity
import com.example.sapiii.constanst.Constant
import com.example.sapiii.databinding.ActivityEditPejantanBinding
import com.example.sapiii.domain.MonitoringPejantan
import com.example.sapiii.util.convertDateToLong
import com.example.sapiii.util.convertLongToTime
import com.example.sapiii.util.toPejantanDomain
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import java.util.*

class EditPejantanActivity : BaseActivity() {

    private var isShow = false
    private var datePicker: MaterialDatePicker<Long>? = null

    private lateinit var binding: ActivityEditPejantanBinding
    private lateinit var PejantanRef: DatabaseReference
    private lateinit var namaH: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditPejantanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        PejantanRef = database.getReference(Constant.REFERENCE_PEJANTAN)
        namaH = intent.getStringExtra("nama") ?: ""

        getDetailPejantan()
        initListener()
    }

    private fun initListener() {
        binding.btnDatePejantan.setOnClickListener {
            if (isShow.not()) {
                datePicker?.show(supportFragmentManager, "dialog") ?: kotlin.run {
                    showToast("Tidak ada data")
                }
                isShow = true
            }
        }

        binding.btnUpdateDetailPejantan.setOnClickListener {
            val nama = binding.tvNamaDetailMutasiKambing.text.toString()
            val kandang = binding.etKodeKandangMk.text.toString()
            val tanggal = binding.etDatePejantan.text.toString()

            val monitoringPejantan = MonitoringPejantan(nama, kandang, tanggal)
            PejantanRef.child(monitoringPejantan.nama).setValue(monitoringPejantan)
                .addOnSuccessListener {
                    showToast("Data berhasil diupdate")
                    finish()
                }
                .addOnFailureListener {
                    showToast("Data gagal diupdate")
                }
        }

        binding.btnDeleteDetailMutasi.setOnClickListener {

            PejantanRef.child(namaH).removeValue()
            finish()

        }
    }

    private fun getDetailPejantan() = with(binding){
        showProgressDialog()
        PejantanRef.child(namaH)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    try {
                        if (snapshot.exists()) {
                            val pejantanData = snapshot.toPejantanDomain()
                            tvNamaDetailMutasiKambing.text = pejantanData.nama
                            etDatePejantan.setText(pejantanData.tanggal)
                            etKodeKandangMk.setText(pejantanData.kandang)


                            datePicker = MaterialDatePicker.Builder.datePicker()
                                .setSelection(Date(convertDateToLong(pejantanData.tanggal)).time)
                                .setTitleText("Pilih Tanggal")
                                .build()

                            datePicker?.addOnPositiveButtonClickListener {
                                etDatePejantan.setText(convertLongToTime(it))
                            }

                            datePicker?.addOnDismissListener {
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