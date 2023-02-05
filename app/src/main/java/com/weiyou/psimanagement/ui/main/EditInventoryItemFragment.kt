package com.weiyou.psimanagement.ui.main

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import com.weiyou.psimanagement.PSIManagamentApplication
import com.weiyou.psimanagement.data.InventoryItem
import com.weiyou.psimanagement.databinding.FragmentEditInventoryItemBinding

/**
 * Fragment to add or update an item in the Inventory database.
 */
class EditInventoryItemFragment : Fragment() {

    // Use the 'by activityViewModels()' Kotlin property delegate from the fragment-ktx artifact
    // to share the ViewModel across fragments.
    private val viewModel: MainViewModel by activityViewModels {
        MainViewModelFactory(
                (activity?.application as PSIManagamentApplication).inventoryItemDatabase.inventoryItemDao(),
                (activity?.application as PSIManagamentApplication).salesItemDatabase.salesItemDao(),
                (activity?.application as PSIManagamentApplication).purchaseItemDatabase.purchaseItemDao(),
                (activity?.application as PSIManagamentApplication).scrapItemDatabase.scrapItemDao()
        )
    }

//    private val navigationArgs: ItemDetailFragmentArgs by navArgs()

    lateinit var inventoryItem: InventoryItem

    // Binding object instance corresponding to the fragment_add_item.xml layout
    // This property is non-null between the onCreateView() and onDestroyView() lifecycle callbacks,
    // when the view hierarchy is attached to the fragment
    private var _binding: FragmentEditInventoryItemBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        activity?.setTitle("先別動修改,很危險")
        _binding = FragmentEditInventoryItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Returns true if the EditTexts are not empty
     */
    private fun isEntryValid(): Boolean {
        return viewModel.isEntryValid(
                binding.itemName.text.toString(),
                binding.itemPrice.text.toString(),
                binding.itemCount.text.toString(),
        )
    }

    /**
     * Binds views with the passed in [item] information.
     */
    private fun bind(inventoryItem: InventoryItem) {
        val price = "%.2f".format(inventoryItem.inventoryItemPrice)
        binding.apply {
            itemName.setText(inventoryItem.inventoryItemName, TextView.BufferType.SPANNABLE)
            itemPrice.setText(price, TextView.BufferType.SPANNABLE)
            itemCount.setText(inventoryItem.inventoryItemQuantityInStock.toString(), TextView.BufferType.SPANNABLE)
            saveAction.setOnClickListener { updateInventoryItem() }
        }
    }


    /**
     * Updates an existing Item in the database and navigates up to list fragment.
     */
    private fun updateInventoryItem() {
        val id = requireArguments().getString("position")
        viewModel.updateInventoryItem(
                Integer.parseInt(id),
                this.binding.itemName.text.toString(),
                java.lang.Double.valueOf(binding.itemPrice.text.toString()),
                Integer.valueOf(this.binding.itemCount.text.toString())
        )
    }

    /**
     * Called when the view is created.
     * The itemId Navigation argument determines the edit item  or add new item.
     * If the itemId is positive, this method retrieves the information from the database and
     * allows the user to update it.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = requireArguments().getString("position")

        Log.d("IANIAN","EditInventory103 id:"+id);
        viewModel.retrieveItem(Integer.parseInt(id)).observe(this.viewLifecycleOwner) { selectedItem ->
            inventoryItem = selectedItem
            bind(inventoryItem)
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

