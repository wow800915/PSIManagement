package com.example.psimanagement.ui.main

import android.os.Bundle
import android.util.Log
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
            (activity?.application as PSIManagamentApplication).inventoryDatabase.inventoryDao(),
            (activity?.application as PSIManagamentApplication).salesDatabase.salesDao(),
            (activity?.application as PSIManagamentApplication).purchaseDatabase.purchaseDao(),
            (activity?.application as PSIManagamentApplication).scrapDatabase.scrapDao()
        )
    }

    private var _binding: FragmentInventoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInventoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//
        val adapter = ItemListAdapter {
//            val action =
//                ItemListFragmentDirections.actionItemListFragmentToItemDetailFragment(it.id)
//            this.findNavController().navigate(action)
            Log.d("IANIAN","ItemListAdapter ");
            val itemDetailFragment = ItemDetailFragment()
            val bundle = Bundle()
            bundle.putString("key", it.inventoryItemId.toString())
            itemDetailFragment.setArguments(bundle)

            requireActivity().supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragmentContainerView, itemDetailFragment, null)//.replace(R.id.fragmentContainerView, AddItemFragment(), null)
                    .commit()
        }
//        binding.itemName.text= viewModel.getInventoryData().toString()
//        Log.d("IANIAN","viewModel.getInventoryData().toString(): "+viewModel.getInventoryData().toString());

        binding.recyclerView.adapter = adapter
        // Attach an observer on the allItems list to update the UI automatically when the data
        // changes.
        viewModel.allItems.observe(this.viewLifecycleOwner) { inventorys ->
            inventorys.let {
                adapter.submitList(it)
            }
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)

        binding.floatingActionButton.setOnClickListener {
//            val action = ItemListFragmentDirections.actionItemListFragmentToAddItemFragment(
//                getString(R.string.add_fragment_title)
//            )
//            this.findNavController().navigate(action)
            Log.d("IANIAN","Inventory82");

            val addItemFragment = AddItemFragment()
            val bundle = Bundle()
            bundle.putString("key", "这是方法二")
            addItemFragment.setArguments(bundle)

            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainerView, addItemFragment, null)//.replace(R.id.fragmentContainerView, AddItemFragment(), null)
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