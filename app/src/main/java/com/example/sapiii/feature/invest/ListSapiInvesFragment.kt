package com.example.sapiii.feature.invest

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sapiii.R
import com.example.sapiii.SapiAdapter2
import com.example.sapiii.base.BaseFragment
import com.example.sapiii.constanst.Constant.REFERENCE_SAPI
import com.example.sapiii.constanst.Constant.statusList
import com.example.sapiii.databinding.FragmentListSapiInvesBinding
import com.example.sapiii.domain.Sapi
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ListSapiInvesFragment : BaseFragment() {

    private lateinit var sapiAdapter: SapiAdapter2
    private lateinit var sapiRecyclerView: RecyclerView
    private lateinit var sapiList: MutableList<Sapi>
    private lateinit var binding: FragmentListSapiInvesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_list_sapi_inves, container, false)
        sapiList = mutableListOf()
        sapiRecyclerView = view.findViewById(R.id.sapi_list_inves)
        sapiAdapter = SapiAdapter2(sapiList)
        sapiRecyclerView.adapter = sapiAdapter
        sapiRecyclerView.layoutManager = LinearLayoutManager(context)

        val database = FirebaseDatabase.getInstance()
        val ref = database.getReference(REFERENCE_SAPI)

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                sapiList.clear()
                for (dataSnapshot in snapshot.children) {
                    val sapi = dataSnapshot.getValue(Sapi::class.java)
                    if (sapi?.data?.status?.lowercase() == statusList[0].lowercase()) {
                        sapiList.add(sapi)
                    }
                }
                sapiAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "Failed to read value.", error.toException())
            }
        })

        return view
    }
}