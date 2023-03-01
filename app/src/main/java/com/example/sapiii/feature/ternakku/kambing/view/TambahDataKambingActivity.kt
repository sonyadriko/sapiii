package com.example.sapiii.feature.ternakku.kambing.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import com.example.sapiii.R
import com.example.sapiii.base.BaseActivity
import com.example.sapiii.databinding.ActivityTambahDataKambingBinding
import com.example.sapiii.domain.*
import com.example.sapiii.feature.ternakku.kambing.viewmodel.TambahDataKambingViewModel
import com.example.sapiii.feature.tips.view.TambhDataArtikelActivity
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.activity_tambh_data_artikel.*

class TambahDataKambingActivity : BaseActivity() {

    private lateinit var binding: ActivityTambahDataKambingBinding
    private val viewModel: TambahDataKambingViewModel by viewModels()
    private lateinit var storageReference: StorageReference
    private var selectedImageUri: Uri? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTambahDataKambingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        storageReference = Firebase.storage.reference.child("images/kambing")

        binding.buttonTambahGambarKambing.setOnClickListener {
            // Membuka galeri untuk memilih gambar
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, TambahDataKambingActivity.REQUEST_SELECT_IMAGE)
        }



        setKelaminDropdown()
        setStatusDropdown()
        initListener()

    }

    private fun initListener() = with(binding) {
        etJenisKelaminKambing.setOnItemClickListener { _, _, i, _ ->
            viewModel.setKelamin(i)
        }

        etStatus.setOnItemClickListener { _, _, i, _ ->
            viewModel.setStatus(i)
        }

        btnSbmitKambing.setOnClickListener {
            showProgressDialog()
            val imageReference =
                storageReference.child(selectedImageUri!!.lastPathSegment!!)

            // Mengunggah gambar ke Firebase Storage
            imageReference.putFile(selectedImageUri!!)
                .addOnSuccessListener {
                    // Mendapatkan URL unduhan gambar
                    imageReference.downloadUrl.addOnSuccessListener { uri ->
                        // Membuat objek artikel
                        val dataKambingNew = Kambing(
                            image = uri.toString(),
                            tag = etNamaKambing.text.toString(),
                            jenis = etJenisKambing.text.toString(),
                            kelamin = viewModel.kelamin,
                            asal = etAsalKambing.text.toString(),
                            kedatangan = Kedatangan(
                                bulan = etKdtgKambing.text.toString(),
                                usia = etUsiadtgKambing.text.toString(),
                                beratBadanAwal = etBrtbdnAwalKambing.text.toString()
                            ),
                            data = DataHewan(
                                usia = etUsiaskgKambing.text.toString(),
                                berat = etBrtbdnSkgKambing.text.toString(),
                                status = viewModel.status
                            ),
                            pemilik = Pemilik(
                                nama = "PT Sedana Farm",
                                noTelepon = "82374121850",
                                alamat = "Kab. Jombang"
                            )
                        )
                        viewModel.addData(dataKambingNew) { isSuccess ->
                            dismissProgressDialog()
                            if (isSuccess) {
                                showToast("Successfully Saved")
                            } else showToast("Failed")
                        }
                    }
                }
                .addOnFailureListener {
                    showToast("Gambar gagal diunggah")
                }


        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == TambahDataKambingActivity.REQUEST_SELECT_IMAGE && resultCode == RESULT_OK && data != null) {
            // Mendapatkan URI gambar yang dipilih dari galeri
            selectedImageUri = data.data
            gambarImageView.setImageURI(selectedImageUri)
        }
    }

    private fun setStatusDropdown() {
        val adapter =
            ArrayAdapter(this@TambahDataKambingActivity, R.layout.list_item, viewModel.statusList)
        binding.etStatus.setAdapter(adapter)
    }

    private fun setKelaminDropdown() {
        val adapter =
            ArrayAdapter(this@TambahDataKambingActivity, R.layout.list_item, viewModel.kelaminList)
        binding.etJenisKelaminKambing.setAdapter(adapter)
    }

    companion object {
        private const val REQUEST_SELECT_IMAGE = 100
    }
}