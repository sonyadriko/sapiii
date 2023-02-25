package com.example.sapiii.feature.tips.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import com.example.sapiii.R
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
            showProgressDialog()
            val judul = binding.etJudulArtikel.text.toString()
            val desc = binding.etDescArtikel.text.toString()
//            val artikel = Artikel(judul, desc)

            try {
                if (selectedImageUri != null && judul.isNotEmpty() && desc.isNotEmpty()) {
                    // Mendapatkan referensi baru untuk gambar yang akan diunggah
                    val imageReference =
                        storageReference.child(selectedImageUri!!.lastPathSegment!!)

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
                                        showToast("Artikel berhasil disimpan")
                                        finish()
                                    }
                                    .addOnFailureListener {
                                        showToast("Artikel gagal disimpan")
                                    }
                            }
                        }
                        .addOnFailureListener {
                            showToast("Gambar gagal diunggah")
                        }
                } else {
                    showToast("Harap lengkapi semua data")
                }
            } catch (e: Exception) {
                showToast(getString(R.string.something_wrong))
            }
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