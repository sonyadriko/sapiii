package com.example.sapiii.feature.perkawinan

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.example.sapiii.ListPejantanFragment
import com.example.sapiii.MutasiKambingFragment
import com.example.sapiii.R
import com.example.sapiii.feature.reproduksi.view.MonitoringKehamilanActivity
import com.example.sapiii.feature.reproduksi.view.MonitoringPejantanActivity

class PerkawinanReproduksiFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v = inflater.inflate(R.layout.fragment_perkawinan_reproduksi, container, false)
        val kehamilan = v.findViewById<LinearLayout>(R.id.monitoring_kehamilan)
        val pejantan = v.findViewById<LinearLayout>(R.id.monitoring_pejantan)

        kehamilan.setOnClickListener {
            startActivity(Intent(activity, MonitoringKehamilanActivity::class.java))
        }

        pejantan.setOnClickListener {
//            startActivity(Intent(activity, MonitoringPejantanActivity::class.java))
            val listMonitoring = ListPejantanFragment()
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.frame_layout, listMonitoring)
                ?.addToBackStack(null)
                ?.commit()
        }

        // Inflate the layout for this fragment
        return v
    }
}