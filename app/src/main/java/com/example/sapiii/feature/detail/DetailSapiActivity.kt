package com.example.sapiii.feature.detail

import android.os.Bundle
import com.example.sapiii.base.BaseActivity
import com.example.sapiii.databinding.ActivityDetailSapiBinding
import com.example.sapiii.domain.Sapi
import com.google.firebase.database.*

class DetailSapiActivity : BaseActivity() {

    private lateinit var binding: ActivityDetailSapiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailSapiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getDetailSapi()
    }

    private fun getDetailSapi() = with(binding) {
        val namasapi = intent.getStringExtra("namasapi")
        val sapiRef = firebaseDB.getReference("sapi")

            sapiRef.child(namasapi.toString())
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {
                            val sapi = snapshot.getValue(Sapi::class.java)

                            namaSapiDetail.text = sapi?.tag
                            jkSapiDetail.text = sapi?.kelamin
                            jenisSapiDetail.text = sapi?.jenis
                            kdtgSapiDetail.text = sapi?.kedatangan?.bulan
                            bbawalSapiDetail.text = sapi?.kedatangan?.beratBadanAwal
                            usiadtgSapiDetail.text = sapi?.kedatangan?.usia
                            usiaskgSapiDetail.text = sapi?.data?.usia
                            bbskgSapiDetail.text = sapi?.data?.berat
                            sttsSapiDetail.text = sapi?.data?.status
                            asalSapiDetail.text = sapi?.asal
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })
    }
}