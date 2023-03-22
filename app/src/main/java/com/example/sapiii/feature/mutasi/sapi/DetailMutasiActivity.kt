package com.example.sapiii.feature.mutasi.sapi

import android.os.Bundle
import com.example.sapiii.base.BaseActivity
import com.example.sapiii.constanst.Constant
import com.example.sapiii.databinding.ActivityDetailMutasiBinding
import com.example.sapiii.domain.MutasiHewan
import com.example.sapiii.util.convertDateToLong
import com.example.sapiii.util.convertLongToTime
import com.example.sapiii.util.toMutasiHewanDomain
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import java.util.*

class DetailMutasiActivity : BaseActivity() {

    private var isShow = false
    private var datePicker: MaterialDatePicker<Long>? = null

    private lateinit var binding: ActivityDetailMutasiBinding
    private lateinit var MutasiRef: DatabaseReference
    private lateinit var namaH: String


    companion object {
        const val RESULT_DELETE = 10
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMutasiBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        val database = Firebase.database
//        val myRef = database.getReference("Mutasi_Kambing")
        MutasiRef = database.getReference(Constant.REFERENCE_MUTASI_SAPI)
        namaH = intent.getStringExtra("nama") ?: ""

        getDetailMutasi()
        initListener()
    }

    private fun initListener() {
        binding.btnDateSapi.setOnClickListener {
            if (isShow.not()) {
                datePicker?.show(supportFragmentManager, "dialog") ?: kotlin.run {
                    showToast("Tidak ada data")
                }
                isShow = true
            }
        }

        binding.btnUpdateDetailMutasi.setOnClickListener {
            val nama = binding.tvNamaDetailMutasi.text.toString()
//            val dateString = binding.etDateSapi.text.toString()
//            val dateFormat = SimpleDateFormat("dd/MM/yyyy")
//            val tanggal: Date = dateFormat.parse(dateString)
            val tanggal = binding.etDateSapi.text.toString()
            val keterangan = binding.spinnerTipeMutasi.selectedItem.toString()

            val mutasiKambing = MutasiHewan(nama, tanggal, keterangan)
            MutasiRef.child(mutasiKambing.nama).setValue(mutasiKambing)
                .addOnSuccessListener {
                    showToast("Mutasi Sapi berhasil diupdate")
                    finish()
                }
                .addOnFailureListener {
                    showToast("Mutasi Sapi gagal diupdate")
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
                            tvNamaDetailMutasi.text = mutasiSap.nama
                            etDateSapi.setText(mutasiSap.tanggal)
                            spinnerTipeMutasi.selectedItem

                            datePicker = MaterialDatePicker.Builder.datePicker()
                                .setSelection(Date(convertDateToLong(mutasiSap.tanggal)).time)
                                .setTitleText("Pilih Tanggal")
                                .build()

                            datePicker?.addOnPositiveButtonClickListener {
                                etDateSapi.setText(convertLongToTime(it))
                            }

                            datePicker?.addOnDismissListener {
                                isShow = false
                            }
                        } else throw Exception("Mutasi sapi is not found")
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