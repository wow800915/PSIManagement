package com.weiyou.psimanagement.ui.main

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.util.Pair
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.weiyou.psimanagement.PSIManagamentApplication
import com.weiyou.psimanagement.R
import com.weiyou.psimanagement.data.InventoryItem
import com.weiyou.psimanagement.databinding.FragmentPurchaseBinding
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
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

    lateinit var inventoryItem: InventoryItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

//        val datePicker =
//            MaterialDatePicker.Builder.datePicker()
//                .setTitleText("Select date")
//                .build()




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

        purchaseItems(adapter,viewModel.calenderTime.year,viewModel.calenderTime.month,viewModel.calenderTime.date,viewModel.calenderTime.year,viewModel.calenderTime.month,viewModel.calenderTime.date)

        binding.tvRangeEnd.setText(SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(viewModel.calenderTime))
        binding.tvRangeStart.setText(SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(Date(viewModel.calenderTime.year,viewModel.calenderTime.month,viewModel.calenderTime.date)))

        binding.btnRangeDay.setOnClickListener {
            purchaseItems(adapter,viewModel.calenderTime.year,viewModel.calenderTime.month,viewModel.calenderTime.date,viewModel.calenderTime.year,viewModel.calenderTime.month,viewModel.calenderTime.date)
            binding.tvRangeEnd.setText(SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(viewModel.calenderTime))
            binding.tvRangeStart.setText(SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(Date(viewModel.calenderTime.year,viewModel.calenderTime.month,viewModel.calenderTime.date)))
        }

        binding.btnRangeWeek.setOnClickListener {
            purchaseItems(adapter,viewModel.calenderTime.year,viewModel.calenderTime.month,viewModel.calenderTime.date-7,viewModel.calenderTime.year,viewModel.calenderTime.month,viewModel.calenderTime.date)
            binding.tvRangeEnd.setText(SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(viewModel.calenderTime))
            binding.tvRangeStart.setText(SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(Date(viewModel.calenderTime.year,viewModel.calenderTime.month,viewModel.calenderTime.date-7)))
        }

        binding.btnRangeMonth.setOnClickListener {
            Log.d("IANIAN","PurchaseFragment112 viewModel.calenderTime:"+viewModel.calenderTime)
            purchaseItems(adapter,viewModel.calenderTime.year,viewModel.calenderTime.month-1,viewModel.calenderTime.date,viewModel.calenderTime.year,viewModel.calenderTime.month,viewModel.calenderTime.date)
            binding.tvRangeEnd.setText(SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(viewModel.calenderTime))
            binding.tvRangeStart.setText(SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(Date(viewModel.calenderTime.year,viewModel.calenderTime.month-1,viewModel.calenderTime.date)))
        }

        binding.btnRangeCustomize.setOnClickListener {
            pickDateRange(adapter)
        }

        binding.btnRangeConfirm.setOnClickListener {
            binding.linechart.visibility = View.VISIBLE
            binding.lvTransactionDate.visibility = View.INVISIBLE
            linechart()
        }

        binding.floatingActionButton.setOnClickListener {

            requireActivity().supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragmentContainerView, AddInventoryItemFragment(), null)//.replace(R.id.fragmentContainerView, AddItemFragment(), null)
                    .commit()

        }

    }

    fun pickDateRange(adapter :PurchaseItemListAdapter){
        val dateRangePicker =
            MaterialDatePicker.Builder.dateRangePicker()
                .setTitleText("Select dates")
                .setSelection(
                    Pair(
                        MaterialDatePicker.thisMonthInUtcMilliseconds(),
                        MaterialDatePicker.todayInUtcMilliseconds()
                    )
                )
                .build()

        dateRangePicker.show(requireActivity().supportFragmentManager, dateRangePicker.toString())

        dateRangePicker.addOnCancelListener{
            Log.d("IANIAN","PurchaseFragment62 Dialog was cancelled");
        }

        dateRangePicker.addOnNegativeButtonClickListener{
            Log.d("IANIAN","PurchaseFragment66 Dialog Negative Button was clicked");
        }

        dateRangePicker.addOnPositiveButtonClickListener{
            Log.d("IANIAN", "Date String = ${dateRangePicker.headerText}::  Date epoch values::${it.first}:: to :: ${it.second}")
            purchaseItems(adapter,Date(it.first).year,Date(it.first).month,Date(it.first).date,Date(it.second).year,Date(it.second).month,Date(it.second).date)
            binding.tvRangeEnd.setText(SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(Date(Date(it.second).year,Date(it.second).month,Date(it.second).date)))
            binding.tvRangeStart.setText(SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(Date(Date(it.first).year,Date(it.first).month,Date(it.first).date)))
        }

    }

    fun purchaseItems(adapter: PurchaseItemListAdapter, fromYear: Int, fromMonth: Int, fromDate: Int, toYear: Int, toMonth: Int, toDate: Int){
        viewModel.purchaseItems(Date(fromYear,fromMonth,fromDate),Date(toYear,toMonth,toDate))
                .observe(this.viewLifecycleOwner) { purchaseitems ->
                    purchaseitems.let {
                        adapter.submitList(it)
                    }
                }

        binding.lvTransactionDate.adapter = adapter
        binding.lvTransactionDate.layoutManager = LinearLayoutManager(this.context)

        binding.lvTransactionDate.visibility = View.VISIBLE
        binding.linechart.visibility = View.INVISIBLE
    }



//1.別放這邊,放在salesFragment
    fun linechart() {

        binding.linechart.isDragEnabled = true
        binding.linechart.setScaleEnabled(false)

        //2.收得陣列放以下的裡面
        viewModel.retrieveItem(0).observe(this.viewLifecycleOwner) {
            selectedItem ->
            inventoryItem = selectedItem

            val yValues: ArrayList<Entry> = ArrayList()

            yValues.add(Entry(0F,50F));
            yValues.add(Entry(1F,inventoryItem.inventoryItemQuantityInStock.toFloat()));
            yValues.add(Entry(2F,20F));
            yValues.add(Entry(3F,30F));
            yValues.add(Entry(4F, 0F));
            yValues.add(Entry(5F,50F));
            yValues.add(Entry(6F,70F));

            val set1 = LineDataSet(yValues, "Data Set:1")

            set1.fillAlpha = 110

            set1.color = Color.RED
            set1.lineWidth = 3f
            set1.valueTextSize = 10f

            val dataSets: ArrayList<ILineDataSet> = ArrayList()
            dataSets.add(set1)

            val data = LineData(dataSets)

            binding.linechart.data = data


            fun a(a:Float ,b:Float){
                yValues.add(Entry(a,b));
            }
        }

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