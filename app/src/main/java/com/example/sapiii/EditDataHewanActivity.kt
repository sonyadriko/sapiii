package com.example.sapiii

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sapiii.base.BaseActivity
import com.example.sapiii.constanst.Constant
import com.example.sapiii.databinding.ActivityEditDataHewanBinding
import com.example.sapiii.databinding.FragmentPakanBinding
import com.example.sapiii.util.convertDateToLong
import com.example.sapiii.util.convertLongToTime
import com.example.sapiii.util.toSapiDomain
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import java.util.*

class EditDataHewanActivity : BaseActivity() {

    private lateinit var binding: ActivityEditDataHewanBinding
    private lateinit var namas: String
    private lateinit var namak: String
    private lateinit var HewanRef: DatabaseReference
    private lateinit var KmbingRef: DatabaseReference






    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditDataHewanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        HewanRef = database.getReference(Constant.REFERENCE_SAPI)
        KmbingRef = database.getReference(Constant.REFERENCE_KAMBING)



        namas = intent.getStringExtra("namasapi") ?: ""
        namak = intent.getStringExtra("namakambing") ?: ""

        getDetailSapi()
    }

    private fun getDetailSapi() = with(binding) {
        HewanRef.child(namas)
            .addListenerForSingleValueEvent(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    try {
                        if (snapshot.exists()) {
                            val detSapi = snapshot.toSapiDomain()
                            tvNamaHewan.text = detSapi.tag
                            etJenisSapi.setText(detSapi.jenis)
                            tvJenisKelamin.text = detSapi.kelamin
                            etKodeKandang.setText(detSapi.kodekandang)


                        } else throw Exception("Mutasi Kambing is not found")
                    } catch (e: Exception) {
                        showToast("error")
                    }

                    dismissProgressDialog()
                }

                override fun onCancelled(error: DatabaseError) {
                    dismissProgressDialog()
                    showToast("cancelled, ${error.message}")
                }

            })
    }


}