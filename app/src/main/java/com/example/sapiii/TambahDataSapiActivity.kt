package com.example.sapiii

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.sapiii.databinding.ActivityTambahDataSapiBinding
import com.example.sapiii.domain.Sapi
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class TambahDataSapiActivity : AppCompatActivity() {

    private lateinit var binding : ActivityTambahDataSapiBinding
    private lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTambahDataSapiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSbmitSapi.setOnClickListener{
            val nmSapi = binding.etNamaSapi.text.toString()
            val jenis = binding.etJenisSapi.text.toString()
            val kdtgn = binding.etKdtgSapi.text.toString()
            val usiadtg = binding.etUsiadtgSapi.text.toString()
            val bbAwal = binding.etBrtbdnAwalSaapi.text.toString()
            val usiaSkg = binding.etUsiaskgSapi.text.toString()
            val bbSkg = binding.etBrtbdnSkgSapi.text.toString()
            val asalSapi = binding.etAsalSapi.text.toString()
            val stts = binding.etSttsskgSapi.text.toString()
            val jkSapi = binding.etJkSapi.text.toString()

            database = FirebaseDatabase.getInstance().getReference("Sapi")
            val sapi = Sapi(nmSapi,jenis,jkSapi,kdtgn,usiadtg,bbAwal,usiaSkg,bbSkg,asalSapi,stts)
            database.child(nmSapi).setValue(sapi).addOnSuccessListener {
                Toast.makeText(this,"Successfully Saved", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener{
                Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show()
            }


        }
    }
}