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
import com.example.psimanagement.databinding.FragmentEditItemBinding
import java.text.DecimalFormat

/**
 * Fragment to add or update an item in the Inventory database.
 */
class EditItemFragment : Fragment() {

    // Use the 'by activityViewModels()' Kotlin property delegate from the fragment-ktx artifact
    // to share the ViewModel across fragments.
    private val viewModel: MainViewModel by activityViewModels {
        Log.d("IANIAN","EditItemFragment28");
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
    private var _binding: FragmentEditItemBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("IANIAN","EditItemFragment52");
        _binding = FragmentEditItemBinding.inflate(inflater, container, false)
        Log.d("IANIAN","EditItemFragment54");
//        bind(inventory);

//        val string = requireArguments().getString("key")
//        Log.d("IANIAN","AddItemFragment54: "+string);
        return binding.root
    }

    /**
     * Returns true if the EditTexts are not empty
     */
    private fun isEntryValid(): Boolean {
        Log.d("IANIAN","EditItemFragment68");
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
        Log.d("IANIAN","EditItemFragment80");
        val price = "%.2f".format(inventory.inventoryItemPrice)
        binding.apply {
            Log.d("IANIAN","EditItemFragment83");
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
        Log.d("IANIAN","EditItemFragment96");
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
        Log.d("IANINN","EditItemFragment112");
//        addNewItem()
        Log.d("IANINN","EditItemFragment114");
        val id = requireArguments().getString("key")
            viewModel.updateInventoryItem(
                Integer.parseInt(id),
                this.binding.itemName.text.toString(),
                java.lang.Double.valueOf(binding.itemPrice.text.toString()),
                Integer.valueOf(this.binding.itemCount.text.toString())
            )
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
        Log.d("IANIAN","EditItemFragment134");
        super.onViewCreated(view, savedInstanceState)
        Log.d("IANIAN","EditItemFragment136");
        val id = requireArguments().getString("key")

//        val id = navigationArgs.itemId
//        if (id > 0) {
            viewModel.retrieveItem(Integer.parseInt(id)).observe(this.viewLifecycleOwner) { selectedItem ->
                inventory = selectedItem
                bind(inventory)
            }
//        } else {
//        binding.saveAction.setOnClickListener {
//            Log.d("IANIAN","EditItemFragment140");
//            addNewItem()
//            Log.d("IANIAN","EditItemFragment142");
//        }
//        }
    }

    /**
     * Called before fragment is destroyed.
     */
    override fun onDestroyView() {
        Log.d("IANIAN","EditItemFragment154");
        super.onDestroyView()
        // Hide keyboard.
        val inputMethodManager = requireActivity().getSystemService(INPUT_METHOD_SERVICE) as
                InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
        _binding = null
    }
}

