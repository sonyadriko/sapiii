package com.example.sapiii.feature.invest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.sapiii.R
import com.example.sapiii.base.BaseFragment
import com.example.sapiii.feature.ternakku.sapi.view.ListSapiFragment

class InvestasiFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v = inflater.inflate(R.layout.fragment_investasi, container, false)
        val invesSapi = v.findViewById<LinearLayout>(R.id.investasi_sapi)
        val invesKambing = v.findViewById<LinearLayout>(R.id.investasi_kambing)

        invesSapi.setOnClickListener {
            val listSapi = ListSapiFragment.fromInves()
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.frame_layout, listSapi)
                ?.addToBackStack(null)
                ?.commit()
        }
        // Inflate the layout for this fragment
        return v
    }
}