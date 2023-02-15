package com.example.sapiii.feature.ternakku

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sapiii.R
import com.example.sapiii.SapiAdapter
import com.example.sapiii.SapiViewModel
import com.example.sapiii.TambahDataSapiActivity
import com.example.sapiii.domain.Sapi
import com.google.firebase.database.DatabaseReference

class ListSapiFragment : Fragment() {


    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private val ARG_PARAM1 = "param1"
    private val ARG_PARAM2 = "param2"

//    private lateinit var dbref : DatabaseReference
//    private lateinit var sapiRecyclerView : RecyclerView
//    private lateinit var sapiArrayList : ArrayList<Sapi>

    private lateinit var viewModel : SapiViewModel
    private lateinit var userRecyclerView : RecyclerView
    lateinit var adapter : SapiAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v = inflater.inflate(R.layout.fragment_list_sapi, container, false)
        val tmbhsapi = v.findViewById<Button>(R.id.btn_tambah_data_sapi)

//        val recyclerView = v.findViewById<RecyclerView>(R.id.sapi_list)
//        recyclerView.layoutManager = LinearLayoutManager(context)



        tmbhsapi.setOnClickListener{
            startActivity(Intent(activity, TambahDataSapiActivity::class.java))
        }

        

        // Inflate the layout for this fragment
        return v
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ListSapiFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userRecyclerView = view.findViewById(R.id.sapi_list)
        userRecyclerView.layoutManager = LinearLayoutManager(context)
        userRecyclerView.setHasFixedSize(true)
        adapter = SapiAdapter()
        userRecyclerView.adapter = adapter

        viewModel = ViewModelProvider(this).get(SapiViewModel::class.java)

        viewModel.allUsers.observe(viewLifecycleOwner, Observer {

            adapter.updateUserList(it)

        })
    }
}