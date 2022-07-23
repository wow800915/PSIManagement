package com.weiyou.psimanagement.ui.main

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
import com.weiyou.psimanagement.databinding.FragmentSalesBinding
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SalesFragment : Fragment() {
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

    private var _binding: FragmentSalesBinding? = null
    private val binding get() = _binding!!

    lateinit var inventoryItem: InventoryItem

    var sss: String? = null
//20210906這邊room開始有改
//    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)

//            sss = viewModel.getUser()
//            Log.d("IANIAN",sss.toString())
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        activity?.setTitle(R.string.title_sales)
        _binding = FragmentSalesBinding.inflate(inflater, container, false)
        return binding.root

//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = SalesItemListAdapter {
        }

        salesItems(adapter,viewModel.calenderTime.year,viewModel.calenderTime.month,viewModel.calenderTime.date,viewModel.calenderTime.year,viewModel.calenderTime.month,viewModel.calenderTime.date)

        binding.tvRangeEnd.setText(SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(viewModel.calenderTime))
        binding.tvRangeStart.setText(SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(Date(viewModel.calenderTime.year,viewModel.calenderTime.month,viewModel.calenderTime.date)))

        binding.btnRangeDay.setOnClickListener {
            salesItems(adapter,viewModel.calenderTime.year,viewModel.calenderTime.month,viewModel.calenderTime.date,viewModel.calenderTime.year,viewModel.calenderTime.month,viewModel.calenderTime.date)
            binding.tvRangeEnd.setText(SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(viewModel.calenderTime))
            binding.tvRangeStart.setText(SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(Date(viewModel.calenderTime.year,viewModel.calenderTime.month,viewModel.calenderTime.date)))
        }

        binding.btnRangeWeek.setOnClickListener {
            salesItems(adapter,viewModel.calenderTime.year,viewModel.calenderTime.month,viewModel.calenderTime.date-7,viewModel.calenderTime.year,viewModel.calenderTime.month,viewModel.calenderTime.date)
            binding.tvRangeEnd.setText(SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(viewModel.calenderTime))
            binding.tvRangeStart.setText(SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(Date(viewModel.calenderTime.year,viewModel.calenderTime.month,viewModel.calenderTime.date-7)))
        }

        binding.btnRangeMonth.setOnClickListener {
            Log.d("IANIAN","PurchaseFragment112 viewModel.calenderTime:"+viewModel.calenderTime)
            salesItems(adapter,viewModel.calenderTime.year,viewModel.calenderTime.month-1,viewModel.calenderTime.date,viewModel.calenderTime.year,viewModel.calenderTime.month,viewModel.calenderTime.date)
            binding.tvRangeEnd.setText(SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(viewModel.calenderTime))
            binding.tvRangeStart.setText(SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(Date(viewModel.calenderTime.year,viewModel.calenderTime.month-1,viewModel.calenderTime.date)))
        }

        binding.btnRangeCustomize.setOnClickListener {
            pickDateRange(adapter)
        }

        binding.btnRangeConfirm.setOnClickListener {
            binding.linechart.visibility = View.VISIBLE
            binding.lvTransactionDate.visibility = View.INVISIBLE
//            linechart()
        }

        binding.floatingActionButton.setOnClickListener {

            val inventoryFragment = InventoryFragment()

            val args = Bundle()
            args.putString("isAddingButtomHided", "isAddingButtomHidedFromSalesFragment")
            inventoryFragment.setArguments(args)


            requireActivity().supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragmentContainerView, inventoryFragment, null)//.replace(R.id.fragmentContainerView, AddItemFragment(), null)
                    .commit()

//            val adapter = InventoryItemListAdapter {
//                val itemDetailFragment = InventoryItemDetailFragment()
//                val bundle = Bundle()
//                bundle.putString("position", it.inventoryItemId.toString())
//                itemDetailFragment.setArguments(bundle)
//
//                requireActivity().supportFragmentManager
//                    .beginTransaction()
//                    .replace(R.id.fragmentContainerView, itemDetailFragment, null)//.replace(R.id.fragmentContainerView, AddItemFragment(), null)
//                    .commit()
//            }
//
//            // Attach an observer on the allItems list to update the UI automatically when the data
//            // changes.
//            viewModel.allInventoryItems.observe(this.viewLifecycleOwner) { inventorys ->
//                inventorys.let {
//                    adapter.submitList(it)
//                }
//            }
//
//            binding.recyclerView.adapter = adapter
//            binding.recyclerView.layoutManager = LinearLayoutManager(this.context)
        }

    }


    fun pickDateRange(adapter :SalesItemListAdapter){
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
            Log.d("IANIAN","SalesFragment62 Dialog was cancelled");
        }

        dateRangePicker.addOnNegativeButtonClickListener{
            Log.d("IANIAN","SalesFragment66 Dialog Negative Button was clicked");
        }

        dateRangePicker.addOnPositiveButtonClickListener{
            Log.d("IANIAN", "Date String = ${dateRangePicker.headerText}::  Date epoch values::${it.first}:: to :: ${it.second}")
            salesItems(adapter,Date(it.first).year,Date(it.first).month,Date(it.first).date,Date(it.second).year,Date(it.second).month,Date(it.second).date)
            binding.tvRangeEnd.setText(SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(Date(Date(it.second).year,Date(it.second).month,Date(it.second).date)))
            binding.tvRangeStart.setText(SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(Date(Date(it.first).year,Date(it.first).month,Date(it.first).date)))
        }

    }


    fun salesItems(adapter: SalesItemListAdapter, fromYear: Int, fromMonth: Int, fromDate: Int, toYear: Int, toMonth: Int, toDate: Int){
        viewModel.salesItems(Date(fromYear,fromMonth,fromDate),Date(toYear,toMonth,toDate))
                .observe(this.viewLifecycleOwner) { salesitems ->
                    salesitems.let {
                        adapter.submitList(it)
                    }
                }

        binding.lvTransactionDate.adapter = adapter
        binding.lvTransactionDate.layoutManager = LinearLayoutManager(this.context)

        binding.lvTransactionDate.visibility = View.VISIBLE
        binding.linechart.visibility = View.INVISIBLE
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MainFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                SalesFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}