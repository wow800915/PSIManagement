package com.example.psimanagement.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.psimanagement.PSIManagamentApplication
import com.example.psimanagement.R
import com.example.psimanagement.data.Inventory
import com.example.psimanagement.databinding.FragmentItemDetailBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

/**
 * [ItemDetailFragment] displays the details of the selected item.
 */
class ItemDetailFragment : Fragment() {
//    private val navigationArgs: ItemDetailFragmentArgs by navArgs()
    lateinit var inventory: Inventory

    private val viewModel: MainViewModel by activityViewModels {
        MainViewModelFactory(
            (activity?.application as PSIManagamentApplication).inventoryDatabase.inventoryDao(),
            (activity?.application as PSIManagamentApplication).salesDatabase.salesDao(),
            (activity?.application as PSIManagamentApplication).purchaseDatabase.purchaseDao(),
            (activity?.application as PSIManagamentApplication).scrapDatabase.scrapDao()
        )
    }

    private var _binding: FragmentItemDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentItemDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Binds views with the passed in item data.
     */
    private fun bind(inventory: Inventory) {
        binding.itemName.text = inventory.inventoryItemName
        binding.itemPrice.text = inventory.inventoryItemPrice.toString()
        binding.itemCount.text = inventory.inventoryItemQuantityInStock.toString()
//        binding.sellItem.isEnabled = viewModel.isStockAvailable(item)
        binding.sellItem.setOnClickListener { viewModel.sellItem(inventory) }
        binding.deleteItem.setOnClickListener { showConfirmationDialog() }
        binding.editItem.setOnClickListener { editItem() }
    }

    /**
     * Navigate to the Edit item screen.
     */
    private fun editItem() {
//        val action = ItemDetailFragmentDirections.actionItemDetailFragmentToAddItemFragment(
//            getString(R.string.edit_fragment_title),
//            item.id
//        )
//        this.findNavController().navigate(action)

        Log.d("IANIAN","ItemListAdapter ");
        val EditItemFragment = EditItemFragment()
        val bundle = Bundle()
        bundle.putString("key", inventory.inventoryItemId.toString())
        EditItemFragment.setArguments(bundle)

        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainerView, EditItemFragment, null)//.replace(R.id.fragmentContainerView, AddItemFragment(), null)
            .commit()

    }

    /**
     * Displays an alert dialog to get the user's confirmation before deleting the item.
     */
    private fun showConfirmationDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(android.R.string.dialog_alert_title))
            .setMessage(getString(R.string.delete_question))
            .setCancelable(false)
            .setNegativeButton(getString(R.string.no)) { _, _ -> }
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                deleteItem()
            }
            .show()
    }

    /**
     * Deletes the current item and navigates to the list fragment.
     */
    private fun deleteItem() {
//        findNavController().navigateUp()
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainerView, InventoryFragment(), null)//.replace(R.id.fragmentContainerView, AddItemFragment(), null)
            .commit()
        viewModel.deleteItem(inventory)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = requireArguments().getString("key")
//        val id = navigationArgs.itemId
        // Retrieve the item details using the itemId.
        // Attach an observer on the data (instead of polling for changes) and only update the
        // the UI when the data actually changes.
        viewModel.retrieveItem(Integer.parseInt(id)).observe(this.viewLifecycleOwner) { selectedItem ->
            inventory = selectedItem
            bind(inventory)
        }
    }

    /**
     * Called when fragment is destroyed
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
