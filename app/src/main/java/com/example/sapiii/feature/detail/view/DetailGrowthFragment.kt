package com.example.sapiii.feature.detail.view

import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.view.ViewGroup.MarginLayoutParams
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.view.marginTop
import androidx.fragment.app.activityViewModels
import com.example.sapiii.base.BaseFragment
import com.example.sapiii.databinding.FragmentDetailGrowthBinding
import com.example.sapiii.domain.Bobot
import com.example.sapiii.feature.detail.viewmodel.DetailViewModel
import com.example.sapiii.repository.KambingRepository
import com.example.sapiii.repository.SapiRepository
import com.example.sapiii.util.toStringBobot
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.utils.ColorTemplate


class DetailGrowthFragment : BaseFragment() {

    private val sapiRepository: SapiRepository = SapiRepository().getInstance()
    private val kambingRepository: KambingRepository = KambingRepository().getInstance()

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
        showProgressDialog()
        when (viewModel.feature) {
            DetailViewModel.Companion.DetailFeature.SAPI -> {
                setData(
                    viewModel.dataSapi.bobot.getBobotTargetList(),
                    viewModel.dataSapi.bobot.getBobotRealList()
                )
                dismissProgressDialog()
            }
            DetailViewModel.Companion.DetailFeature.KAMBING -> {
                setData(
                    viewModel.dataKambing.bobot.getBobotTargetList(),
                    viewModel.dataKambing.bobot.getBobotRealList()
                )
                dismissProgressDialog()
            }
            DetailViewModel.Companion.DetailFeature.UNKNOWN -> {}
        }
    }

    private fun initListener() {
        binding.buttonEdit.setOnClickListener {
            showDialogInput()
        }

        binding.buttonDelete.setOnClickListener {
            deleteData()
        }

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
            animateX(500)
            // draw legend
            legend.form = Legend.LegendForm.LINE
        }
    }

    private fun deleteData() {
        val bobotTarget = viewModel.dataSapi.bobot.getBobotTargetList().toMutableList()
        val bobotAsli = viewModel.dataSapi.bobot.getBobotRealList().toMutableList()

        if (bobotTarget.isNotEmpty() && bobotAsli.isNotEmpty()) {
            showProgressDialog()
            bobotTarget.removeAt(bobotTarget.lastIndex)
            bobotAsli.removeAt(bobotTarget.lastIndex)

            val newBobot = Bobot(
                bobotTarget = bobotTarget.toStringBobot(),
                bobotReal = bobotAsli.toStringBobot()
            )

            viewModel.updateBobot(newBobot)
            sapiRepository.updateSapi(
                viewModel.dataSapi.copy(
                    bobot = newBobot
                )
            ) {
                dismissProgressDialog()
                if (it) {
                    setData(bobotTarget, bobotAsli)
                    binding.cartGraph.invalidate()
                    showToast("Data berhasil diubah")
                } else {
                    showToast()
                }
            }
        } else showToast("data bobot kosong")
    }

    private fun showDialogInput() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        //you should edit this to fit your needs
        //you should edit this to fit your needs
        builder.setTitle("Input data bobot")

        val layoutParams1 =
            LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        val layoutParams2 =
            LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        val layoutParams3 =
            LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)

        val one = EditText(context)
        one.layoutParams = layoutParams1
        (one.layoutParams as LinearLayout.LayoutParams).setMargins(0, 4, 0, 8)
        one.hint = "bobot target" //optional
        one.inputType = InputType.TYPE_CLASS_NUMBER

        val two = EditText(context)
        two.layoutParams = layoutParams2
        (two.layoutParams as LinearLayout.LayoutParams).setMargins(0, 4, 0, 8)
        two.hint = "bobot sekarang" //optional
        two.inputType = InputType.TYPE_CLASS_NUMBER

        val lay = LinearLayout(context)
        lay.layoutParams = layoutParams3
        lay.setPadding(60, 30, 60, 0)
        lay.orientation = LinearLayout.VERTICAL
        lay.addView(TextView(context).apply { text = "Masukkan bobot target" })
        lay.addView(one)
        lay.addView(TextView(context).apply { text = "Masukkan bobot asli" })
        lay.addView(two)
        builder.setView(lay)

        // Set up the buttons

        // Set up the buttons
        builder.setPositiveButton("Ok") { dialog, _ -> //get the two inputs
            dialog.dismiss()
            showProgressDialog()
            val target: Int = one.text.toString().toInt()
            val real: Int = two.text.toString().toInt()

            when (viewModel.feature) {
                DetailViewModel.Companion.DetailFeature.SAPI -> {
                    val bobotTarget = viewModel.dataSapi.bobot.getBobotTargetList().toMutableList()
                    val bobotAsli = viewModel.dataSapi.bobot.getBobotRealList().toMutableList()
                    bobotTarget.add(target)
                    bobotAsli.add(real)

                    val newBobot = Bobot(
                        bobotTarget = bobotTarget.toStringBobot(),
                        bobotReal = bobotAsli.toStringBobot()
                    )

                    viewModel.updateBobot(newBobot)
                    sapiRepository.updateSapi(
                        viewModel.dataSapi.copy(
                            bobot = newBobot
                        )
                    ) {
                        dismissProgressDialog()
                        if (it) {
                            setData(bobotTarget, bobotAsli)
                            binding.cartGraph.invalidate()
                            showToast("Data berhasil diubah")
                        } else {
                            showToast()
                        }
                    }
                }
                DetailViewModel.Companion.DetailFeature.KAMBING -> {
                    val bobotTarget =
                        viewModel.dataKambing.bobot.getBobotTargetList().toMutableList()
                    val bobotAsli = viewModel.dataKambing.bobot.getBobotRealList().toMutableList()
                    bobotTarget.add(target)
                    bobotAsli.add(real)

                    val newBobot = Bobot(
                        bobotTarget = bobotTarget.toStringBobot(),
                        bobotReal = bobotAsli.toStringBobot()
                    )

                    viewModel.updateBobot(newBobot)
                    kambingRepository.updateKambing(
                        viewModel.dataKambing.copy(
                            bobot = newBobot
                        )
                    ) {
                        dismissProgressDialog()
                        if (it) {
                            setData(bobotTarget, bobotAsli)
                            binding.cartGraph.invalidate()
                            showToast("Data berhasil diubah")
                        } else {
                            showToast()
                        }
                    }
                }
                DetailViewModel.Companion.DetailFeature.UNKNOWN -> {}
            }
        }

        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }
        builder.show()
    }

    private fun setData(target: List<Int>, real: List<Int>) = with(binding) {
        val values1: ArrayList<Entry> = ArrayList()
        val values2: ArrayList<Entry> = ArrayList()

        for (i in real.indices) {
            val newFloat: Float = real[i].toFloat()
            values2.add(Entry(i.toFloat(), newFloat))
        }

        for (i in target.indices) {
            val newFloat: Float = target[i].toFloat()
            values1.add(Entry(i.toFloat(), newFloat))
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
            data.setValueTextColor(Color.BLACK)
            data.setValueTextSize(24f)
            data.setValueTextSize(9f)

            // set data
            cartGraph.data = data
        }
    }
}