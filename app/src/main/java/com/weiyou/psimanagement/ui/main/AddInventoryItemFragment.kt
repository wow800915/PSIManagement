package com.weiyou.psimanagement.ui.main

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.weiyou.psimanagement.PSIManagamentApplication
import com.weiyou.psimanagement.R
import com.weiyou.psimanagement.data.InventoryItem
import com.weiyou.psimanagement.databinding.FragmentAddInventoryItemBinding

/**
 * Fragment to add or update an item in the Inventory database.
 */
class AddInventoryItemFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels {
        MainViewModelFactory(
                (activity?.application as PSIManagamentApplication).inventoryItemDatabase.inventoryItemDao(),
                (activity?.application as PSIManagamentApplication).salesItemDatabase.salesItemDao(),
                (activity?.application as PSIManagamentApplication).purchaseItemDatabase.purchaseItemDao(),
                (activity?.application as PSIManagamentApplication).scrapItemDatabase.scrapItemDao()
        )
    }

    lateinit var inventoryItem: InventoryItem

    private var _binding: FragmentAddInventoryItemBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        activity?.setTitle(R.string.title_purchase)
        _binding = FragmentAddInventoryItemBinding.inflate(inflater, container, false)
        return binding.root
    }


    private fun isEntryValid(): Boolean {
        return viewModel.isEntryValid(
                binding.itemName.text.toString(),
                binding.itemPrice.text.toString(),
                binding.itemCount.text.toString()
        )
    }

    /**
     * Inserts the new Item into database and navigates up to list fragment.
     */
    private fun addNewItem() {
        if (isEntryValid()) {
            val doubleValueOfInventoryPrice: Double = java.lang.Double.valueOf(binding.itemPrice.text.toString())
            val intValueOfInventoryItemQuantityInStock: Int = Integer.decode(binding.itemCount.text.toString())
            viewModel.purchaseItem(
                    binding.itemName.text.toString(), doubleValueOfInventoryPrice, intValueOfInventoryItemQuantityInStock
            )
        }
    }


    /**
     * Called when the view is created.
     * The itemId Navigation argument determines the edit item  or add new item.
     * If the itemId is positive, this method retrieves the information from the database and
     * allows the user to update it.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.saveAction.setOnClickListener {
            addNewItem()
            requireActivity().supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragmentContainerView, InventoryFragment(), null)//.replace(R.id.fragmentContainerView, AddItemFragment(), null)
                    .commit()
        }
    }

    /**
     * Called before fragment is destroyed.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        // Hide keyboard.
        val inputMethodManager = requireActivity().getSystemService(INPUT_METHOD_SERVICE) as
                InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
        _binding = null
    }
}
