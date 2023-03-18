package com.example.sapiii.feature.detail.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.example.sapiii.R
import com.example.sapiii.base.BaseActivity
import com.example.sapiii.databinding.ActivityDetailSapiBinding
import com.example.sapiii.domain.Sapi
import com.example.sapiii.feature.detail.viewmodel.DetailViewModel
import com.example.sapiii.feature.home.HomeFragment

class DetailHewanActivity : BaseActivity() {
    private lateinit var binding: ActivityDetailSapiBinding
    private lateinit var namaSapi: String

    private val viewModel: DetailViewModel by viewModels()

    companion object {
        const val RESULT_DELETE = 10
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailSapiBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        setContentView(binding.root)

        initListener()
        initBundle()
        observe()

        replaceFragment(DetailHewanFragment())
    }

    private fun initListener() = with(binding) {
        isDetailSelected = true

        buttonDetail.setOnClickListener {
            isDetailSelected = true
            replaceFragment(DetailHewanFragment())
        }

        buttonGrowth.setOnClickListener {
            isDetailSelected = false
            replaceFragment(DetailGrowthFragment())
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.fragmentDetailContainer.id, fragment)
        fragmentTransaction.commit()
    }

    private fun observe() {
        viewModel.viewState.observe(this) {
            @Suppress("WHEN_ENUM_CAN_BE_NULL_IN_JAVA")
            when (it) {
                DetailViewModel.ViewState.INITIAL -> {
                    dismissProgressDialog()
                }
                DetailViewModel.ViewState.LOADING -> {
                    showProgressDialog()
                }
                DetailViewModel.ViewState.FAIL -> {
                    finish()
                }
            }
        }
    }

    private fun initBundle() {
        namaSapi = intent.getStringExtra("namasapi") ?: ""
        if (namaSapi.isEmpty()) {
            viewModel.initBundle(DetailViewModel.Companion.DetailFeature.SAPI, intent?.data)
        } else viewModel.initBundle(DetailViewModel.Companion.DetailFeature.SAPI, namaSapi)
    }

    override fun onBackPressed() {
        if (namaSapi.isEmpty()) {
            goToHomepage()
        } else super.onBackPressed()
    }
}