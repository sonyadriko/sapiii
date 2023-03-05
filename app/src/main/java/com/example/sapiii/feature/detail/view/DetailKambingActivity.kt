package com.example.sapiii.feature.detail.view

import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.sapiii.R
import com.example.sapiii.base.BaseActivity
import com.example.sapiii.constanst.Constant
import com.example.sapiii.databinding.ActivityDetailKambingBinding
import com.example.sapiii.util.toKambingDomain
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

class DetailKambingActivity : BaseActivity() {
    private lateinit var binding: ActivityDetailKambingBinding
    private lateinit var kmbgRef: DatabaseReference
    private lateinit var namaKambing: String

    companion object {
        const val RESULT_DELETE = 10
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailKambingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        kmbgRef = database.getReference(Constant.REFERENCE_KAMBING)
        namaKambing = intent.getStringExtra("namakambing") ?: ""
        initListener()
        getDetailKambing()
    }

    private fun initListener() = with(binding) {
        buttonDelete.setOnClickListener {
            kmbgRef.child(namaKambing).removeValue { error, _ ->
                if (error == null) {
                    setResult(RESULT_DELETE)
                    finish()
                }
            }
        }
    }

    private fun getDetailKambing() = with(binding) {
        kmbgRef.child(namaKambing)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    try {
                        if (snapshot.exists()) {
                            val kambing = snapshot.toKambingDomain()
                            val imageKambing = kambing.image
                            Glide.with(this@DetailKambingActivity)
                                .load(imageKambing)
                                .placeholder(R.drawable.ic_outline_image_24)
                                .into(ivKmbg)
                            namaKmbgDetail.text = kambing.tag
                            jkKmbgDetail.text = kambing.kelamin
                            jenisKmbgDetail.text = kambing.jenis
                            kdtgKmbgDetail.text = kambing.kedatangan.bulan
                            bbawalKmbgDetail.text = kambing.kedatangan.beratBadanAwal
                            usiadtgKmbgDetail.text = kambing.kedatangan.usia
                            usiaskgKmbgDetail.text = kambing.data.usia
                            bbskgKmbgDetail.text = kambing.data.berat
                            sttsKmbgDetail.text = kambing.data.status
                            asalKmbgDetail.text = kambing.asal

                            tvNamaPemilik.text = kambing.pemilik.nama
                            tvNomorTelpon.text = kambing.pemilik.noTelepon
                            tvAlamatPemilik.text = kambing.pemilik.alamat
                        } else throw Exception("Kambing is not found")
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