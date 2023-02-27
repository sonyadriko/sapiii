package com.example.sapiii.feature.tips.view

import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.sapiii.R
import com.example.sapiii.base.BaseActivity
import com.example.sapiii.constanst.Constant
import com.example.sapiii.databinding.ActivityDetailArtikelBinding
import com.example.sapiii.util.toArtikelDomain
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

class DetailArtikelActivity : BaseActivity() {

    private lateinit var binding: ActivityDetailArtikelBinding
    private lateinit var ArtikelRef: DatabaseReference
    private lateinit var judulAr: String

    companion object {
        const val RESULT_DELETE = 10
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailArtikelBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ArtikelRef = database.getReference(Constant.REFERENCE_ARTIKEL)
        judulAr = intent.getStringExtra("judularti") ?: ""
        getDetailArtikel()
    }

    private fun getDetailArtikel() = with(binding) {
        ArtikelRef.child(judulAr)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    try {
                        if (snapshot.exists()) {
                            val artikel = snapshot.toArtikelDomain()
                            headingArtikel.text = artikel.judul
                            val imageAr = artikel.image
                            Glide.with(this@DetailArtikelActivity)
                                .load(imageAr)
                                .placeholder(R.drawable.ic_outline_image_24)
                                .into(imageArtikel)
                            newsArtikel.text = artikel.desc

                        } else throw Exception("Artikel is not found")
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