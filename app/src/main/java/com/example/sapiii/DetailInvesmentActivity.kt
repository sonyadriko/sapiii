package com.example.sapiii

import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.sapiii.base.BaseActivity
import com.example.sapiii.constanst.Constant
import com.example.sapiii.databinding.ActivityDetailInvesmentBinding
import com.example.sapiii.util.toSapiDomain
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

class DetailInvesmentActivity : BaseActivity() {

    private lateinit var binding: ActivityDetailInvesmentBinding
    private lateinit var sapiRef: DatabaseReference
    private lateinit var namaSapi: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailInvesmentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sapiRef = database.getReference(Constant.REFERENCE_KAMBING)
        namaSapi = intent.getStringExtra("namasapi") ?: ""
        getDetailSapi()
    }

    private fun getDetailSapi() = with(binding) {
        sapiRef.child(namaSapi)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    try {
                        if (snapshot.exists()) {
                            val sapi = snapshot.toSapiDomain()
                            val imageSapi = sapi.image
                            Glide.with(this@DetailInvesmentActivity)
                                .load(imageSapi)
                                .placeholder(R.drawable.ic_outline_image_24)
                                .into(ivSapi)
                            namaSapiMutasi.text = sapi.tag
                            inputHarga.setText(sapi.harga)
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