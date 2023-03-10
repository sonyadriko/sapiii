package com.example.sapiii.feature.ternakku.sapi.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import com.example.sapiii.R
import com.example.sapiii.base.BaseActivity
import com.example.sapiii.constanst.Constant.statusList
import com.example.sapiii.databinding.ActivityTambahDataSapiBinding
import com.example.sapiii.domain.DataHewan
import com.example.sapiii.domain.Kedatangan
import com.example.sapiii.domain.Pemilik
import com.example.sapiii.domain.Sapi
import com.example.sapiii.feature.ternakku.sapi.viewmodel.TambahDataSapiViewModel
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.activity_tambh_data_artikel.*

class TambahDataSapiActivity : BaseActivity() {

    private lateinit var binding: ActivityTambahDataSapiBinding
    private val viewModel: TambahDataSapiViewModel by viewModels()
    private lateinit var storageReference: StorageReference
    private var selectedImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTambahDataSapiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        storageReference = Firebase.storage.reference.child("images/sapi")

        binding.buttonTambahGambarSapi.setOnClickListener {
            // Membuka galeri untuk memilih gambar
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, TambahDataSapiActivity.REQUEST_SELECT_IMAGE)
        }

        setKelaminDropdown()
        setStatusDropdown()
        initListener()
    }

    private fun setStatusDropdown() {
        val adapter =
            ArrayAdapter(this@TambahDataSapiActivity, R.layout.list_item, statusList)
        binding.etStatus.setAdapter(adapter)
    }

    private fun setKelaminDropdown() {
        val adapter =
            ArrayAdapter(this@TambahDataSapiActivity, R.layout.list_item, viewModel.kelaminList)
        binding.etJenisKelamin.setAdapter(adapter)
    }

    private fun initListener() = with(binding) {
        etJenisKelamin.setOnItemClickListener { _, _, i, _ ->
            viewModel.setKelamin(i)
        }

        etStatus.setOnItemClickListener { _, _, i, _ ->
            viewModel.setStatus(i)
        }

        btnSbmitSapi.setOnClickListener {
            val imageReference =
                storageReference.child(selectedImageUri!!.lastPathSegment!!)

            // Mengunggah gambar ke Firebase Storage
            imageReference.putFile(selectedImageUri!!)
                .addOnSuccessListener {
                    // Mendapatkan URL unduhan gambar
                    imageReference.downloadUrl.addOnSuccessListener { uri ->
                        // Membuat objek artikel
                        val dataSapiNew = Sapi(
                            image = uri.toString(),
                            tag = etNamaSapi.text.toString(),
                            jenis = etJenisSapi.text.toString(),
                            kelamin = viewModel.kelamin,
                            asal = etAsalSapi.text.toString(),
                            kedatangan = Kedatangan(
                                bulan = etKdtgSapi.text.toString(),
                                usia = etUsiadtgSapi.text.toString(),
                                beratBadanAwal = etBrtbdnAwalSaapi.text.toString()
                            ),
                            data = DataHewan(
                                usia = etUsiaskgSapi.text.toString(),
                                berat = etBrtbdnSkgSapi.text.toString(),
                                status = viewModel.status
                            ),
                            pemilik = Pemilik(
                                nama = "PT Sedana Farm",
                                noTelepon = "82374121850",
                                alamat = "Kab. Jombang"
                            )
                        )

                        viewModel.addData(dataSapiNew) { isSuccess ->
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

        if (requestCode == TambahDataSapiActivity.REQUEST_SELECT_IMAGE && resultCode == RESULT_OK && data != null) {
            // Mendapatkan URI gambar yang dipilih dari galeri
            selectedImageUri = data.data
            gambarImageView.setImageURI(selectedImageUri)
        }
    }


    companion object {
        private const val REQUEST_SELECT_IMAGE = 100
    }
}