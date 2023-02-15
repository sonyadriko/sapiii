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

//    private lateinit var dbref : DatabaseReference
//    private lateinit var sapiRecyclerView : RecyclerView
//    private lateinit var sapiArrayList : ArrayList<Sapi>

    private lateinit var viewModel : SapiViewModel
    private lateinit var userRecyclerView: RecyclerView
    lateinit var adapter: SapiAdapter

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
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Home.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            com.example.sapiii.ListSapiFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }

        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"
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