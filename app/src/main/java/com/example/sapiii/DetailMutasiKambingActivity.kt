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
import com.example.sapiii.util.toMutasiKambingDomain
import com.example.sapiii.util.toMutasiSapiDomain
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

class DetailMutasiKambingActivity : BaseActivity() {

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

        binding.btnUpdateDetailMutasi.setOnClickListener {
            val nama = binding.tvNamaDetailMutasiKambing.text.toString()
//            val dateString = binding.etDateSapi.text.toString()
//            val dateFormat = SimpleDateFormat("dd/MM/yyyy")
//            val tanggal: Date = dateFormat.parse(dateString)
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
//                            etDateSapi.setText(d.toString())


                        } else throw Exception("Mutasi Kambing is not found")
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