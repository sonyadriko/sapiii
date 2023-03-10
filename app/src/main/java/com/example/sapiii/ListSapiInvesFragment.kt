package com.example.sapiii

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sapiii.databinding.FragmentListSapiBinding
import com.example.sapiii.databinding.FragmentListSapiInvesBinding
import com.example.sapiii.domain.Sapi
import com.example.sapiii.feature.ternakku.sapi.view.ListSapiFragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ListSapiInvesFragment : Fragment() {

    private lateinit var sapiAdapter: SapiAdapter2
    private lateinit var sapiRecyclerView: RecyclerView
    private lateinit var sapiList: MutableList<Sapi>
    private lateinit var binding: FragmentListSapiInvesBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        binding = FragmentListSapiInvesBinding.inflate(layoutInflater, container, false)

        val view = inflater.inflate(R.layout.fragment_list_sapi_inves, container, false)
        sapiList = mutableListOf()
        sapiRecyclerView = view.findViewById(R.id.sapi_list_inves)
        sapiAdapter = SapiAdapter2(sapiList)
        sapiRecyclerView.adapter = sapiAdapter
        sapiRecyclerView.layoutManager = LinearLayoutManager(context)

        val database = FirebaseDatabase.getInstance()
        val ref = database.getReference("sapi")

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                sapiList.clear()
                for (dataSnapshot in snapshot.children) {
                    val sapi = dataSnapshot.getValue(Sapi::class.java)
                    if (sapi?.data?.status == "aktif") {
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