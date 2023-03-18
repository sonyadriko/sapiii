package com.example.sapiii.feature.detail.view

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.sapiii.base.BaseFragment
import com.example.sapiii.databinding.FragmentDetailGrowthBinding
import com.example.sapiii.feature.detail.viewmodel.DetailViewModel
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.utils.ColorTemplate

class DetailGrowthFragment : BaseFragment() {

    private val viewModel: DetailViewModel by activityViewModels()
    private lateinit var binding: FragmentDetailGrowthBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDetailGrowthBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
    }

    private fun initListener() {
        binding.cartGraph.apply {
            setBackgroundColor(Color.WHITE)
            description.isEnabled = false

            // enable gestures
            setTouchEnabled(true)
            setDrawGridBackground(true)

            // enable scaling and dragging
            isDragEnabled = true
            setScaleEnabled(true)
            setPinchZoom(true)

            // axis setting
            xAxis.enableGridDashedLine(10f, 10f, 0f)
            axisLeft.enableGridDashedLine(10f, 10f, 0f)
            axisLeft.axisMinimum = 0f
            axisLeft.mAxisMaximum = 200f

            // animate
            animateX(1500)

            setData(5, 200f)

            // draw legend
            legend.form = Legend.LegendForm.LINE
        }
    }

    private fun setData(count: Int, range: Float) = with(binding) {

        val values1: ArrayList<Entry> = ArrayList()

        for (i in 0 until count) {
            val newFloat: Float = ((Math.random() * (range / 2f)) + 50).toFloat()
            values1.add(Entry(i.toFloat(), newFloat))
        }

        val values2: ArrayList<Entry> = ArrayList()

        for (i in 0 until count) {
            val newFloat: Float = ((Math.random() * range) + 450).toFloat()
            values2.add(Entry(i.toFloat(), newFloat))
        }

        val values3: ArrayList<Entry> = ArrayList()

        for (i in 0 until count) {
            val newFloat: Float = ((Math.random() * range) + 500).toFloat()
            values3.add(Entry(i.toFloat(), newFloat))
        }

        val set1: LineDataSet
        val set2: LineDataSet

        if (cartGraph.data != null &&
            cartGraph.data.dataSetCount > 0
        ) {
            set1 = cartGraph.data.getDataSetByIndex(0) as LineDataSet
            set2 = cartGraph.data.getDataSetByIndex(1) as LineDataSet
            set1.values = values1
            set2.values = values2
            cartGraph.data.notifyDataChanged()
            cartGraph.notifyDataSetChanged()
        } else {
            // create a dataset and give it a type
            set1 = LineDataSet(values1, "Data Bobot Badan Real")

            set1.axisDependency = YAxis.AxisDependency.LEFT
            set1.color = Color.parseColor("#2c05f2")
            set1.setCircleColor(Color.WHITE)
            set1.lineWidth = 2f
            set1.circleRadius = 3f
            set1.fillAlpha = 65
            set1.fillColor = ColorTemplate.getHoloBlue()
            set1.highLightColor = Color.rgb(244, 117, 117)
            set1.setDrawCircleHole(false)
            //set1.setFillFormatter(new MyFillFormatter(0f))
            //set1.setDrawHorizontalHighlightIndicator(false)
            //set1.setVisible(false)
            //set1.setCircleHoleColor(Color.WHITE)

            // create a dataset and give it a type
            set2 = LineDataSet(values2, "Target Bobot Badan")
            set2.axisDependency = YAxis.AxisDependency.RIGHT
            set2.color = Color.parseColor("#f9953b")
            set2.setCircleColor(Color.WHITE)
            set2.lineWidth = 2f
            set2.circleRadius = 3f
            set2.fillAlpha = 65
            set2.fillColor = Color.RED
            set2.setDrawCircleHole(false)
            set2.highLightColor = Color.rgb(244, 117, 117)
            //set2.setFillFormatter(new MyFillFormatter(900f))

            // create a data object with the data sets
            val data = LineData(set1, set2)
            data.setValueTextColor(Color.WHITE)
            data.setValueTextSize(9f)

            // set data
            cartGraph.data = data
        }
    }
}