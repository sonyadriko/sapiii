package com.example.sapiii

import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import com.example.sapiii.base.BaseActivity
import com.example.sapiii.constanst.Constant
import com.example.sapiii.databinding.ActivityDetailMutasiBinding
import com.example.sapiii.domain.MutasiKambing
import com.example.sapiii.feature.detail.viewmodel.DetailViewModel
import com.example.sapiii.feature.mutasi.sapi.MutasiSapiViewModel
import com.example.sapiii.util.toMutasiSapiDomain
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAccessor
import java.util.*

class DetailMutasiActivity : BaseActivity() {

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

        binding.btnUpdateDetailMutasi.setOnClickListener {
            val nama = binding.tvNamaDetailMutasi.text.toString()
//            val dateString = binding.etDateSapi.text.toString()
//            val dateFormat = SimpleDateFormat("dd/MM/yyyy")
//            val tanggal: Date = dateFormat.parse(dateString)
            val tanggal = binding.etDateSapi.text.toString()
            val keterangan = binding.spinnerTipeMutasi.selectedItem.toString()

            val mutasiKambing = MutasiKambing(nama, tanggal, keterangan)
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
        MutasiRef.child(namaH)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                @RequiresApi(Build.VERSION_CODES.O)
                override fun onDataChange(snapshot: DataSnapshot) {
                    try {
                        if (snapshot.exists()) {
                            val mutasiSap = snapshot.toMutasiSapiDomain()
                            tvNamaDetailMutasi.text = mutasiSap.nama


                            etDateSapi.setText(mutasiSap.tanggal)

                            spinnerTipeMutasi.selectedItem
//                            etDateSapi.setText(d.toString())


                        } else throw Exception("Mutasi sapi is not found")
                    } catch (e: Exception) {
                        showToast("error")
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    showToast("cancelled, ${error.message}")
                }
            })
    }
}

