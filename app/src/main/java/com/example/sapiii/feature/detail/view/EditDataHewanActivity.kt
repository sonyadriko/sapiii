package com.example.sapiii.feature.detail.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ArrayAdapter
import com.bumptech.glide.Glide
import com.example.sapiii.R
import com.example.sapiii.base.BaseActivity
import com.example.sapiii.constanst.Constant
import com.example.sapiii.constanst.Constant.statusList
import com.example.sapiii.databinding.ActivityEditDataHewanBinding
import com.example.sapiii.util.toSapiDomain
import com.example.sapiii.domain.*
import com.example.sapiii.feature.detail.viewmodel.DetailViewModel
import com.example.sapiii.feature.ternakku.sapi.view.TambahDataSapiActivity
import com.example.sapiii.feature.ternakku.sapi.view.TambahDataSapiActivity.Companion.REQUEST_SELECT_IMAGE
import com.example.sapiii.repository.KambingRepository
import com.example.sapiii.repository.SapiRepository
import com.example.sapiii.util.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.activity_tambh_data_artikel.*

class EditDataHewanActivity : BaseActivity() {

    private lateinit var binding: ActivityEditDataHewanBinding
    private lateinit var namaSapi: String
    private lateinit var namaKambing: String

    private val sapiRepository: SapiRepository = SapiRepository().getInstance()
    private val kambingRepository: KambingRepository = KambingRepository().getInstance()

    private var dataKambing: Kambing = Kambing()
    private var dataSapi: Sapi = Sapi()

    private lateinit var selectedHewan: DetailViewModel.Companion.DetailFeature

    // storage and images
    private lateinit var storageReference: StorageReference
    private var selectedImageUri: Uri? = null

    // database references
    private lateinit var sapiRef: DatabaseReference
    private lateinit var kambingRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditDataHewanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sapiRef = database.getReference(Constant.REFERENCE_SAPI)
        kambingRef = database.getReference(Constant.REFERENCE_KAMBING)

        initBundle()
        setStatusDropdown()
        initListener()
    }

    private fun initListener() {
        binding.buttonTambahGambar.setOnClickListener {
            // Membuka galeri untuk memilih gambar
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, REQUEST_SELECT_IMAGE)
        }

        binding.btnSbmit.setOnClickListener {
            showProgressDialog()
            when (selectedHewan) {
                DetailViewModel.Companion.DetailFeature.SAPI -> updateDataSapi()
                DetailViewModel.Companion.DetailFeature.KAMBING -> updateDataKambing()
                DetailViewModel.Companion.DetailFeature.UNKNOWN -> {
                    dismissProgressDialog()
                }
            }
        }

        binding.etStatus.setOnItemClickListener { _, _, i, _ ->
            when (selectedHewan) {
                DetailViewModel.Companion.DetailFeature.SAPI -> {
                    dataSapi = dataSapi.copy(
                        data = dataSapi.data.copy(status = statusList[i])
                    )
                }
                DetailViewModel.Companion.DetailFeature.KAMBING -> {
                    dataKambing = dataKambing.copy(
                        data = dataKambing.data.copy(status = statusList[i])
                    )
                }
                DetailViewModel.Companion.DetailFeature.UNKNOWN -> {}
            }
        }
    }

    private fun updateDataKambing() {
        storageReference = Firebase.storage.reference.child("images/kambing")

        if (selectedImageUri != null) {
            val imageReference = storageReference.child(selectedImageUri?.lastPathSegment ?: "")
            // Mengunggah gambar ke Firebase Storage
            imageReference.putFile(selectedImageUri!!)
                .addOnSuccessListener {
                    // Mendapatkan URL unduhan gambar
                    imageReference.downloadUrl.addOnSuccessListener { uri ->
                        updateKambing(uri)
                    }
                }
                .addOnFailureListener {
                    dismissProgressDialog()
                    showToast("Gambar gagal diunggah")
                }
        } else updateKambing(null)
    }

    private fun updateDataSapi() {
        storageReference = Firebase.storage.reference.child("images/sapi")
        if (selectedImageUri != null) {
            val imageReference = storageReference.child(selectedImageUri?.lastPathSegment ?: "")
            // Mengunggah gambar ke Firebase Storage
            imageReference.putFile(selectedImageUri!!)
                .addOnSuccessListener {
                    // Mendapatkan URL unduhan gambar
                    imageReference.downloadUrl.addOnSuccessListener { uri ->
                        updateSapi(uri)
                    }
                }
                .addOnFailureListener {
                    dismissProgressDialog()
                    showToast("Gambar gagal diunggah")
                }
        } else updateSapi(null)
    }

    private fun updateKambing(uri: Uri?) {
        val dataKambingNew = dataKambing.copy(
            image = uri.toString(),
            jenis = binding.etJenis.text.toString(),
            asal = binding.etAsal.text.toString(),
            kodekandang = binding.etKodeKandang.text.toString(),
            idpmk = binding.etPmk.text.toString(),
            kedatangan = Kedatangan(
                kedatanganHewan = binding.etKdtg.text.toString(),
                usiaKedatangan = binding.etUsiadtg.text.toString(),
                beratBadanAwal = binding.etBrtbdnAwal.text.toString()
            ),
            data = dataKambing.data.copy(
                usia = binding.etUsiaskg.text.toString(),
                beratBadan = binding.etBrtbdnSkg.text.toString().toDouble()
            )
        )

        val request =
            if (uri != null) dataKambingNew.copy(image = uri.toString()) else dataKambingNew
        kambingRepository.updateKambing(kambing = request) {
            dismissProgressDialog()
            if (it) {
                setResult(RESULT_OK)
                finish()
            }
            else showToast()
        }
    }

    private fun updateSapi(uri: Uri?) {
        // Membuat objek Sapi
        val dataSapiNew = dataSapi.copy(
            jenis = binding.etJenis.text.toString(),
            asal = binding.etAsal.text.toString(),
            kodekandang = binding.etKodeKandang.text.toString(),
            idpmk = binding.etPmk.text.toString(),
            kedatangan = Kedatangan(
                kedatanganHewan = binding.etKdtg.text.toString(),
                usiaKedatangan = binding.etUsiadtg.text.toString(),
                beratBadanAwal = binding.etBrtbdnAwal.text.toString()
            ),
            data = dataSapi.data.copy(
                usia = binding.etUsiaskg.text.toString(),
                beratBadan = binding.etBrtbdnSkg.text.toString().toDouble()
            )
        )

        val request = if (uri != null) dataSapiNew.copy(image = uri.toString()) else dataSapiNew
        sapiRepository.updateSapi(sapi = request) {
            dismissProgressDialog()
            if (it) {
                setResult(RESULT_OK)
                finish()
            }
            else showToast()
        }
    }

    private fun setStatusDropdown() {
        val adapter = ArrayAdapter(this, R.layout.list_item, statusList)
        binding.etStatus.setAdapter(adapter)
    }

    private fun initBundle() {
        namaSapi = intent.getStringExtra("namasapi") ?: ""
        namaKambing = intent.getStringExtra("namakambing") ?: ""

        showProgressDialog()
        if (namaKambing.isNotEmpty()) {
            selectedHewan = DetailViewModel.Companion.DetailFeature.KAMBING
            getDetailKambing(namaKambing)
        } else if (namaSapi.isNotEmpty()) {
            selectedHewan = DetailViewModel.Companion.DetailFeature.SAPI
            getDetailSapi(namaSapi)
        } else {
            dismissProgressDialog()
            showToast()
            finish()
        }
    }

    private fun getDetailSapi(namaSapi: String) {
        sapiRef.child(namaSapi)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    try {
                        if (snapshot.exists()) {
                            val detSapi = snapshot.toSapiDomain()

                            setDetailSapi(detSapi)
                        } else throw Exception("Mutasi Kambing is not found")
                    } catch (e: Exception) {
                        showToast(e.message)
                    }

                    dismissProgressDialog()
                }

                override fun onCancelled(error: DatabaseError) {
                    dismissProgressDialog()
                    showToast("cancelled, ${error.message}")
                }
            })
    }

    private fun getDetailKambing(namaSapi: String) {
        kambingRef.child(namaSapi)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    try {
                        if (snapshot.exists()) {
                            val detKambing = snapshot.toKambingDomain()
                            setDetailKambing(detKambing)
                        } else throw Exception("Mutasi Kambing is not found")
                    } catch (e: Exception) {
                        showToast(e.message)
                    }

                    dismissProgressDialog()
                }

                override fun onCancelled(error: DatabaseError) {
                    dismissProgressDialog()
                    showToast("cancelled, ${error.message}")
                }
            })
    }

    private fun setDetailKambing(kambing: Kambing?) = with(binding) {
        if (kambing != null) {
            dataKambing = kambing

            Glide.with(gambarImageView)
                .load(kambing.image)
                .placeholder(R.drawable.ic_outline_image_24)
                .into(gambarImageView)

            etJenis.setText(kambing.jenis)
            etKodeKandang.setText(kambing.kodekandang)
            etPmk.setText(kambing.idpmk)

            // kedatangan
            etKdtg.setText(kambing.kedatangan.kedatanganHewan)
            etUsiadtg.setText(kambing.kedatangan.usiaKedatangan)
            etBrtbdnAwal.setText(kambing.kedatangan.beratBadanAwal)

            // data sekarang
            etUsiaskg.setText(kambing.data.usia)
            etBrtbdnSkg.setText(kambing.data.beratBadan.toString())
            etAsal.setText(kambing.asal)
            etStatus.hint = dataKambing.data.status
        } else showToast("Kambing is not found")
    }

    private fun setDetailSapi(sapi: Sapi?) = with(binding) {
        if (sapi != null) {
            dataSapi = sapi

            Glide.with(gambarImageView)
                .load(sapi.image)
                .placeholder(R.drawable.ic_outline_image_24)
                .into(gambarImageView)

            etJenis.setText(sapi.jenis)
            etKodeKandang.setText(sapi.kodekandang)
            etPmk.setText(sapi.idpmk)

            // kedatangan
            etKdtg.setText(sapi.kedatangan.kedatanganHewan)
            etUsiadtg.setText(sapi.kedatangan.usiaKedatangan)
            etBrtbdnAwal.setText(sapi.kedatangan.beratBadanAwal)

            // data sekarang
            etUsiaskg.setText(sapi.data.usia)
            etBrtbdnSkg.setText(sapi.data.beratBadan.toString())
            etAsal.setText(sapi.asal)
            etStatus.hint = dataSapi.data.status
        } else showToast("data Sapi tidak ditemukan")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == TambahDataSapiActivity.REQUEST_SELECT_IMAGE && resultCode == RESULT_OK && data != null) {
            // Mendapatkan URI gambar yang dipilih dari galeri
            selectedImageUri = data.data
            gambarImageView.setImageURI(selectedImageUri)
        }
    }
}