package com.example.psimanagement.ui.main

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
import com.example.psimanagement.PSIManagamentApplication
import com.example.psimanagement.data.Inventory
import com.example.psimanagement.databinding.FragmentAddItemBinding
import com.google.android.gms.common.util.NumberUtils

/**
 * Fragment to add or update an item in the Inventory database.
 */
class AddItemFragment : Fragment() {

    // Use the 'by activityViewModels()' Kotlin property delegate from the fragment-ktx artifact
    // to share the ViewModel across fragments.
    private val viewModel: MainViewModel by activityViewModels {
        Log.d("IANIAN","AddItemFragment28");
        MainViewModelFactory(
            (activity?.application as PSIManagamentApplication).inventoryDatabase.inventoryDao(),
            (activity?.application as PSIManagamentApplication).salesDatabase.salesDao(),
            (activity?.application as PSIManagamentApplication).purchaseDatabase.purchaseDao(),
            (activity?.application as PSIManagamentApplication).scrapDatabase.scrapDao()
        )
    }

//    private val navigationArgs: ItemDetailFragmentArgs by navArgs()

    lateinit var inventory: Inventory

    // Binding object instance corresponding to the fragment_add_item.xml layout
    // This property is non-null between the onCreateView() and onDestroyView() lifecycle callbacks,
    // when the view hierarchy is attached to the fragment
    private var _binding: FragmentAddItemBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("IANIAN","AddItemFragment52");
        _binding = FragmentAddItemBinding.inflate(inflater, container, false)
        Log.d("IANIAN","AddItemFragment54");
//        bind(inventory);
        return binding.root
    }

    /**
     * Returns true if the EditTexts are not empty
     */
    private fun isEntryValid(): Boolean {
        Log.d("IANIAN","AddItemFragment68");
        return viewModel.isEntryValid(
            binding.itemName.text.toString(),
            binding.itemPrice.text.toString(),
            binding.itemCount.text.toString(),
        )
    }

    /**
     * Binds views with the passed in [item] information.
     */
    private fun bind(inventory: Inventory) {
        Log.d("IANIAN","AddItemFragment80");
        val price = "%.2f".format(inventory.inventoryItemPrice)
        binding.apply {
            Log.d("IANIAN","AddItemFragment83");
            itemName.setText(inventory.inventoryItemName, TextView.BufferType.SPANNABLE)
            itemPrice.setText(price, TextView.BufferType.SPANNABLE)
            itemCount.setText(inventory.inventoryItemQuantityInStock.toString(), TextView.BufferType.SPANNABLE)
            saveAction.setOnClickListener { updateItem() }
        }
    }

    /**
     * Inserts the new Item into database and navigates up to list fragment.
     */
    private fun addNewItem() {
//        if (isEntryValid()) {
        val doubleValueOfInventoryPrice: Double = java.lang.Double.valueOf(binding.itemPrice.text.toString())
        val intValueOfInventoryItemQuantityInStock: Int = Integer.decode(binding.itemCount.text.toString())
        Log.d("IANIAN","AddItemFragment96");
            viewModel.addNewInventoryItem(
                binding.itemName.text.toString(), doubleValueOfInventoryPrice, intValueOfInventoryItemQuantityInStock
            )
//            val action = AddItemFragmentDirections.actionAddItemFragmentToItemListFragment()
//            findNavController().navigate(action)
//        }
    }

    /**
     * Updates an existing Item in the database and navigates up to list fragment.
     */
    private fun updateItem() {
//        if (isEntryValid()) {
        Log.d("IANINN","AddItemFragment112");
            addNewItem()
        Log.d("IANINN","AddItemFragment114");

//            viewModel.updateItem(
//                this.navigationArgs.itemId,
//                this.binding.itemName.text.toString(),
//                this.binding.itemPrice.text.toString(),
//                this.binding.itemCount.text.toString()
//            )
//            val action = AddItemFragmentDirections.actionAddItemFragmentToItemListFragment()
//            findNavController().navigate(action)
//        }
    }

    /**
     * Called when the view is created.
     * The itemId Navigation argument determines the edit item  or add new item.
     * If the itemId is positive, this method retrieves the information from the database and
     * allows the user to update it.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("IANIAN","AddItemFragment134");
        super.onViewCreated(view, savedInstanceState)
        Log.d("IANIAN","AddItemFragment136");
//        val id = navigationArgs.itemId
//        if (id > 0) {
//            viewModel.retrieveItem(id).observe(this.viewLifecycleOwner) { selectedItem ->
//                item = selectedItem
//                bind(item)
//            }
//        } else {
            binding.saveAction.setOnClickListener {
                Log.d("IANIAN","AddItemFragment140");
                addNewItem()
                Log.d("IANIAN","AddItemFragment142");
            }
//        }
    }

    /**
     * Called before fragment is destroyed.
     */
    override fun onDestroyView() {
        Log.d("IANIAN","AddItemFragment154");
        super.onDestroyView()
        // Hide keyboard.
        val inputMethodManager = requireActivity().getSystemService(INPUT_METHOD_SERVICE) as
                InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
        _binding = null
    }
}
