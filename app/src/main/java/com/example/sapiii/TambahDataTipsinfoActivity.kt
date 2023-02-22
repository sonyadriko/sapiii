package com.example.sapiii

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sapiii.base.BaseActivity
import com.example.sapiii.databinding.ActivityTambahDataTipsInfoBinding
import com.example.sapiii.domain.Artikel
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class AddTipsInfoActivity : BaseActivity() {

    private lateinit var binding: ActivityTambahDataTipsInfoBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTambahDataTipsInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val database = Firebase.database
        val myRef = database.getReference("Artikel")
        binding.btnSbmitArtikel.setOnClickListener {

            val judul = binding.etJudulArtikel.text.toString()
            val desc = binding.etDescArtikel.text.toString()
            val artikel = Artikel(judul, desc)

            myRef.push().setValue(artikel)
                .addOnSuccessListener {
                    showToast("Artikel Berhasil")
                }
                .addOnFailureListener {
                    showToast("Gagal Menambahkan")
                }

        }
        initListener()
    }

    private fun initListener() = with(binding) {

    }
}