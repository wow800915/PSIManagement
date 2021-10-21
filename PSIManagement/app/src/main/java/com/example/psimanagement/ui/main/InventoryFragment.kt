package com.example.psimanagement.ui.main

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
import com.example.psimanagement.databinding.FragmentInventoryBinding


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

        binding.recyclerView.adapter = adapter
        // Attach an observer on the allItems list to update the UI automatically when the data
        // changes.
        viewModel.allInventoryItems.observe(this.viewLifecycleOwner) { inventorys ->
            inventorys.let {
                adapter.submitList(it)
            }
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)

        binding.floatingActionButton.setOnClickListener {

            requireActivity().supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragmentContainerView, AddInventoryItemFragment(), null)//.replace(R.id.fragmentContainerView, AddItemFragment(), null)
                    .commit()

        }
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