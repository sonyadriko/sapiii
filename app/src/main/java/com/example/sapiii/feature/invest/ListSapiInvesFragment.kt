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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sapiii.DetailInvesmentActivity
import com.example.sapiii.R
import com.example.sapiii.base.BaseFragment
import com.example.sapiii.constanst.Constant.REFERENCE_SAPI
import com.example.sapiii.constanst.Constant.statusList
import com.example.sapiii.databinding.FragmentListSapiInvesBinding
import com.example.sapiii.domain.Sapi
import com.example.sapiii.feature.detail.view.DetailSapiActivity
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

    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == DetailSapiActivity.RESULT_DELETE) {
                loadSapi()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_list_sapi_inves, container, false)
        sapiList = mutableListOf()
        recyclerView = view.findViewById(R.id.sapi_list_inves)
        adapter = SapiAdapter2(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)



        loadSapi()

        return view
    }

    private fun loadSapi() {
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

    override fun onClick(data: Any, position: Int) {
        val currentItem = data as Sapi
        val detailIntent = Intent(context, DetailInvesmentActivity::class.java).apply {
            putExtra("namasapi", currentItem.tag)
            putExtra("jeniskelamin", currentItem.kelamin)
        }
        startForResult.launch(detailIntent)
    }
}