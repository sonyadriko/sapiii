package com.example.sapiii.feature.detail

import android.os.Bundle
import com.example.sapiii.base.BaseActivity
import com.example.sapiii.databinding.ActivityDetailSapiBinding
import com.example.sapiii.util.toSapiDomain
import com.google.firebase.database.*

class DetailSapiActivity : BaseActivity() {
    private lateinit var binding: ActivityDetailSapiBinding
    private lateinit var sapiRef: DatabaseReference
    private lateinit var namaSapi: String

    companion object {
        const val RESULT_DELETE = 10
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailSapiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sapiRef = firebaseDB.getReference("Sapi")
        namaSapi = intent.getStringExtra("namasapi") ?: ""
        initListener()
        getDetailSapi()
    }

    private fun initListener() = with(binding) {
        buttonDelete.setOnClickListener {
            sapiRef.child(namaSapi).removeValue { error, _ ->
                if (error == null) {
                    setResult(RESULT_DELETE)
                    finish()
                }
            }
        }
    }

    private fun getDetailSapi() = with(binding) {
        sapiRef.child(namaSapi)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    try {
                        if (snapshot.exists()) {
                            val sapi = snapshot.toSapiDomain()

                            namaSapiDetail.text = sapi.tag
                            jkSapiDetail.text = sapi.kelamin
                            jenisSapiDetail.text = sapi.jenis
                            kdtgSapiDetail.text = sapi.kedatangan.bulan
                            bbawalSapiDetail.text = sapi.kedatangan.beratBadanAwal
                            usiadtgSapiDetail.text = sapi.kedatangan.usia
                            usiaskgSapiDetail.text = sapi.data.usia
                            bbskgSapiDetail.text = sapi.data.berat
                            sttsSapiDetail.text = sapi.data.status
                            asalSapiDetail.text = sapi.asal

                            tvNamaPemilik.text = sapi.pemilik.nama
                            tvNomorTelpon.text = sapi.pemilik.noTel
                            tvAlamatPemilik.text = sapi.pemilik.alamat
                        } else throw Exception("Sapi is not found")
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