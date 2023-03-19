package com.example.sapiii

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sapiii.databinding.ActivityEditDataHewanBinding
import com.example.sapiii.databinding.FragmentPakanBinding

class EditDataHewanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditDataHewanBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditDataHewanBinding.inflate(layoutInflater)
        setContentView(binding.root)




    }
}