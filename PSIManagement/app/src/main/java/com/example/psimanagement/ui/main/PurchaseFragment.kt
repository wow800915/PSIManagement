package com.example.psimanagement.ui.main

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.psimanagement.PSIManagamentApplication
import com.example.psimanagement.R
import com.example.psimanagement.databinding.FragmentPurchaseBinding
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PurchaseFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PurchaseFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val viewModel: MainViewModel by activityViewModels {
        MainViewModelFactory(
            (activity?.application as PSIManagamentApplication).inventoryItemDatabase.inventoryItemDao(),
            (activity?.application as PSIManagamentApplication).salesItemDatabase.salesItemDao(),
            (activity?.application as PSIManagamentApplication).purchaseItemDatabase.purchaseItemDao(),
            (activity?.application as PSIManagamentApplication).scrapItemDatabase.scrapItemDao()
        )
    }

    private var _binding: FragmentPurchaseBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.setTitle(R.string.title_purchase)
        _binding = FragmentPurchaseBinding.inflate(inflater, container, false)
        return binding.root
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_purchase, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//
        val adapter = PurchaseItemListAdapter {
        }

        // Attach an observer on the allItems list to update the UI automatically when the data
        // changes.
        viewModel.todayPurchaseItems.observe(this.viewLifecycleOwner) { purchases ->
            purchases.let {
                adapter.submitList(it)
            }
        }

        binding.lvTransactionDate.adapter = adapter
        binding.lvTransactionDate.layoutManager = LinearLayoutManager(this.context)

//這邊以ＭＶＶＭ邏輯,看一下是不是放這邊是不是正常的
        binding.btnRangeWeek.setOnClickListener {
            viewModel.weekPurchaseItems.observe(this.viewLifecycleOwner) { purchases ->
                purchases.let {
                    adapter.submitList(it)
                }
            }
            binding.lvTransactionDate.adapter = adapter
            binding.lvTransactionDate.layoutManager = LinearLayoutManager(this.context)
        }

//        binding.btnRangeConfirm.setOnClickListener {
            linechart()
//        }

    }
//別放這邊
    fun linechart() {
        //            val mChart: LineChart
//
//            mChart = findViewById(R.id.linechart) as LineChart

//        mChart.setOnChartGestureListener(MainActivity.this);
//        mChart.setOnChartValueSelectedListener(MainActivity.this);


//        mChart.setOnChartGestureListener(MainActivity.this);
//        mChart.setOnChartValueSelectedListener(MainActivity.this);
        binding.linechart.isDragEnabled = true
        binding.linechart.setScaleEnabled(false)

//            val yValues: ArrayList<Map.Entry<*, *>> = ArrayList()
//
//            yValues.add(
//                MutableMap.MutableEntry<Any?, Any?>(
//                    0,
//                    60f
//                )
//            )
//            yValues.add(
//                MutableMap.MutableEntry<Any?, Any?>(
//                    1,
//                    50f
//                )
//            )
//            yValues.add(
//                MutableMap.MutableEntry<Any?, Any?>(
//                    2,
//                    70f
//                )
//            )
//            yValues.add(
//                MutableMap.MutableEntry<Any?, Any?>(
//                    3,
//                    30f
//                )
//            )
//            yValues.add(
//                MutableMap.MutableEntry<Any?, Any?>(
//                    4,
//                    50f
//                )
//            )
//            yValues.add(
//                MutableMap.MutableEntry<Any?, Any?>(
//                    5,
//                    60f
//                )
//            )
//            yValues.add(
//                MutableMap.MutableEntry<Any?, Any?>(
//                    6,
//                    65f
//                )
//            )
        val yValues: ArrayList<Entry> = ArrayList()

        yValues.add(Entry(0F,50f));
        yValues.add(Entry(1F,60f));
        yValues.add(Entry(2F,20f));
        yValues.add(Entry(3F,30f));
        yValues.add(Entry(4F,60f));
        yValues.add(Entry(5F,50f));
        yValues.add(Entry(6F,70f));

        val set1 = LineDataSet(yValues, "Data Set:1")

        set1.fillAlpha = 110

        set1.color = Color.RED
        set1.lineWidth = 3f
        set1.valueTextSize = 10f

        val dataSets: ArrayList<ILineDataSet> = ArrayList()
        dataSets.add(set1)

        val data = LineData(dataSets)

        binding.linechart.data = data
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PurchaseFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PurchaseFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}