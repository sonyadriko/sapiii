package com.example.sapiii.feature.detail.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.example.sapiii.base.BaseActivity
import com.example.sapiii.base.BaseFragment
import com.example.sapiii.databinding.ActivityDetailHewanBinding
import com.example.sapiii.feature.detail.viewmodel.DetailViewModel

class DetailHewanActivity : BaseActivity() {
    private lateinit var binding: ActivityDetailHewanBinding
    private lateinit var namaSapi: String
    private lateinit var namaKambing: String

    private val viewModel: DetailViewModel by viewModels()

    companion object {
        const val RESULT_DELETE = 10
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailHewanBinding.inflate(layoutInflater)
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
                    val f = supportFragmentManager.findFragmentById(binding.fragmentDetailContainer.id)
                    if (f is BaseFragment) f.dismissProgressDialog()
                }
                DetailViewModel.ViewState.LOADING -> {
                    val f = supportFragmentManager.findFragmentById(binding.fragmentDetailContainer.id)
                    if (f is BaseFragment)
                        f.showProgressDialog()
                }
                DetailViewModel.ViewState.FAIL -> {
                    finish()
                }
            }
        }
    }

    private fun initBundle() {
        namaSapi = intent.getStringExtra("namasapi") ?: ""
        namaKambing = intent.getStringExtra("namakambing") ?: ""

        if (namaKambing.isNotEmpty()) {
            viewModel.initBundle(DetailViewModel.Companion.DetailFeature.KAMBING, namaKambing)
        } else if (namaSapi.isNotEmpty()) {
            viewModel.initBundle(DetailViewModel.Companion.DetailFeature.SAPI, namaSapi)
        } else {
            viewModel.initBundle(intent?.data)
        }
    }

    override fun onBackPressed() {
        if (namaSapi.isEmpty() && namaKambing.isEmpty()) {
            goToHomepage()
        } else super.onBackPressed()
    }
}