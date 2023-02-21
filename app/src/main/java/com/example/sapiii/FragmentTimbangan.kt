package com.example.sapiii

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.sapiii.databinding.FragmentListSapiBinding
import com.example.sapiii.feature.ternakku.sapi.view.ListSapiFragment
import com.example.sapiii.feature.timbangan.HitungBobotKambingActivity
import com.example.sapiii.feature.timbangan.HitungBobotSapiActivity

class FragmentTimbangan : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_timbangan, container, false)

        val timbangansapi = v.findViewById<LinearLayout>(R.id.timbangan_sapi)
        val timbangankambing = v.findViewById<LinearLayout>(R.id.timbangan_kambing)

        timbangansapi.setOnClickListener {
            val intent = Intent(context,HitungBobotSapiActivity::class.java)
            startActivity(intent)
        }
//        timbangansapi.setOnClickListener {
//            val listSapi = ListSapiFragment.fromTimbangan()
//            activity?.supportFragmentManager?.beginTransaction()
//                ?.replace(R.id.frame_layout, listSapi)
//                ?.addToBackStack(null)
//                ?.commit()
//        }

        timbangankambing.setOnClickListener {
            val intent = Intent(context, HitungBobotKambingActivity::class.java)
            startActivity(intent)
        }

        return v
    }


}