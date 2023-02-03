package com.example.sapiii.feature

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.example.sapiii.databinding.ActivityHomeBinding
import com.example.sapiii.R
import com.example.sapiii.feature.home.HomeFragment


class HomeActivity : AppCompatActivity() {

    private lateinit var binding : ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(HomeFragment())

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.home -> replaceFragment(HomeFragment())

                else -> {

                }
            }
            true
        }
    }

    private fun replaceFragment(homeFragment: HomeFragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, homeFragment)
        fragmentTransaction.commit()
    }
}