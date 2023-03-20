package com.example.sapiii.feature.invest

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sapiii.R
import com.example.sapiii.base.BaseFragment
import com.example.sapiii.constanst.Constant.REFERENCE_SAPI
import com.example.sapiii.constanst.Constant.statusList
import com.example.sapiii.databinding.FragmentListSapiInvesBinding
import com.example.sapiii.domain.Sapi
import com.example.sapiii.feature.detail.view.DetailHewanActivity
import com.example.sapiii.feature.mutasi.kambing.MutasiKambingAdapter
import com.example.sapiii.feature.ternakku.sapi.viewmodel.SapiViewModel
import com.example.sapiii.util.OnItemClick
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ListSapiInvesFragment : BaseFragment(), OnItemClick {

    private lateinit var adapter: SapiAdapter2
    private lateinit var recyclerView: RecyclerView
    private lateinit var sapiList: MutableList<Sapi>
    private lateinit var binding: FragmentListSapiInvesBinding

    private lateinit var from: String

    val database = FirebaseDatabase.getInstance()
    val ref = database.getReference(REFERENCE_SAPI)

    private val viewModel: SapiViewModel by viewModels()

    companion object {
        const val RESULT_DELETE = 10
    }

    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == DetailInvesmentActivity.RESULT_DELETE) {
                loadSapiInves()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListSapiInvesBinding.inflate(layoutInflater)


        setupRecyclerView()
        loadSapiInves()

        return binding.root
    }

    private fun loadSapiInves() {

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                sapiList.clear()
                for (dataSnapshot in snapshot.children) {
                    val sapi = dataSnapshot.getValue(Sapi::class.java)
                    if (sapi?.data?.status?.lowercase() == statusList[0].lowercase()) {
                        sapiList.add(sapi)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "Failed to read value.", error.toException())
            }
        })
    }

    private fun setupRecyclerView() {
//        val mutasiKambingList = binding.mutasiKambingList
//        mutasiKambingList.adapter = mutasiKambbingAdapter

        recyclerView = binding.sapiListInves
//        userRecyclerView.addItemDecoration(dividerItemDecoration)
        recyclerView.setHasFixedSize(true)
        adapter = SapiAdapter2(this)
        recyclerView.adapter = adapter
    }

    override fun onClick(data: Any, position: Int) {
        val currentItem = data as Sapi
        val detailIntent = Intent(context, DetailInvesmentActivity::class.java).apply {
            putExtra("namasapiinves", currentItem.tag)
        }
        startForResult.launch(detailIntent)
    }
}