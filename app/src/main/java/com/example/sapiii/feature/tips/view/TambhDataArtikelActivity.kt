package com.example.sapiii.feature.tips.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import com.example.sapiii.base.BaseActivity
import com.example.sapiii.databinding.ActivityTambhDataArtikelBinding
import com.example.sapiii.domain.Artikel
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.activity_tambh_data_artikel.*

class TambhDataArtikelActivity : BaseActivity() {

    private lateinit var binding: ActivityTambhDataArtikelBinding
//    private val REQUEST_SELECT_IMAGE = 100

    private lateinit var storageReference: StorageReference
    private var selectedImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTambhDataArtikelBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val database = Firebase.database
        val myRef = database.getReference("Artikel")
        storageReference = Firebase.storage.reference.child("images/artikel")

        binding.buttonTambahGambarArtikel.setOnClickListener {
            // Membuka galeri untuk memilih gambar
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, REQUEST_SELECT_IMAGE)
        }

        binding.btnSbmitArtikel.setOnClickListener {

            val judul = binding.etJudulArtikel.text.toString()
            val desc = binding.etDescArtikel.text.toString()
//            val artikel = Artikel(judul, desc)

            if (selectedImageUri != null && judul.isNotEmpty() && desc.isNotEmpty()) {
                // Mendapatkan referensi baru untuk gambar yang akan diunggah
                val imageReference = storageReference.child(selectedImageUri!!.lastPathSegment!!)

                // Mengunggah gambar ke Firebase Storage
                imageReference.putFile(selectedImageUri!!)
                    .addOnSuccessListener {
                        // Mendapatkan URL unduhan gambar
                        imageReference.downloadUrl.addOnSuccessListener { uri ->
                            // Membuat objek artikel
                            val artikel = Artikel(uri.toString(), judul, desc)

                            // Menyimpan objek artikel ke Firebase Realtime Database
                            myRef.push().setValue(artikel)
                                .addOnSuccessListener {
                                    Toast.makeText(
                                        this,
                                        "Artikel berhasil disimpan",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    finish()
                                }
                                .addOnFailureListener {
                                    Toast.makeText(
                                        this,
                                        "Artikel gagal disimpan",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                        }
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Gambar gagal diunggah", Toast.LENGTH_SHORT).show()
                    }
            } else {
                Toast.makeText(this, "Harap lengkapi semua data", Toast.LENGTH_SHORT).show()
            }

//            myRef.push().setValue(artikel)
//                .addOnSuccessListener {
//                    showToast("Artikel Berhasil")
//                }
//                .addOnFailureListener {
//                    showToast("Gagal Menambahkan")
//                }

        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_SELECT_IMAGE && resultCode == RESULT_OK && data != null) {
            // Mendapatkan URI gambar yang dipilih dari galeri
            selectedImageUri = data.data
            gambarImageView.setImageURI(selectedImageUri)
        }
    }

    companion object {
        private const val REQUEST_SELECT_IMAGE = 100
    }
}