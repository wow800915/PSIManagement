package com.weiyou.psimanagement.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.weiyou.psimanagement.PSIManagamentApplication
import com.weiyou.psimanagement.R
import com.weiyou.psimanagement.databinding.FragmentInventoryBinding


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [InventoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class InventoryFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    var isAddingButtomHided: String? = null

    private val viewModel: MainViewModel by activityViewModels {
        MainViewModelFactory(
                (activity?.application as PSIManagamentApplication).inventoryItemDatabase.inventoryItemDao(),
                (activity?.application as PSIManagamentApplication).salesItemDatabase.salesItemDao(),
                (activity?.application as PSIManagamentApplication).purchaseItemDatabase.purchaseItemDao(),
                (activity?.application as PSIManagamentApplication).scrapItemDatabase.scrapItemDao()
        )
    }

    private var _binding: FragmentInventoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }
//        isAddingButtomHidedFromSalesFragment =  getArguments().getString("isAddingButtomHidedFromSalesFragment", null);
//        getArguments()?.getString("isAddingButtomHidedFromSalesFragment", null)?.let { Log.d("IANIAN", it) };
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        activity?.setTitle(R.string.title_inventory)
        _binding = FragmentInventoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(getArguments()?.getString("isAddingButtomHided", null).equals("isAddingButtomHidedFromSalesFragment")){
            binding.floatingActionButton.visibility= View.INVISIBLE
            activity?.setTitle(R.string.title_select_sales_items)
        }else if(getArguments()?.getString("isAddingButtomHided", null).equals("isAddingButtomHidedFromScrapFragment")){
            binding.floatingActionButton.visibility= View.INVISIBLE
            activity?.setTitle(R.string.title_select_scrap_items)
        }

//
        val adapter = InventoryItemListAdapter {
            val itemDetailFragment = InventoryItemDetailFragment()
            val bundle = Bundle()
            bundle.putString("position", it.inventoryItemId.toString())
            itemDetailFragment.setArguments(bundle)

            requireActivity().supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragmentContainerView, itemDetailFragment, null)//.replace(R.id.fragmentContainerView, AddItemFragment(), null)
                    .commit()
        }

        // Attach an observer on the allItems list to update the UI automatically when the data
        // changes.
        viewModel.allInventoryItems.observe(this.viewLifecycleOwner) { inventorys ->
            inventorys.let {
                adapter.submitList(it)
            }
        }

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)

        binding.floatingActionButton.setOnClickListener {

            requireActivity().supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragmentContainerView, AddInventoryItemFragment(), null)//.replace(R.id.fragmentContainerView, AddItemFragment(), null)
                    .commit()

        }


//        if(isAddingButtomHidedFromSalesFragment==true){
//            binding.floatingActionButton.visibility = View.INVISIBLE
//        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment InventoryFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                InventoryFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}