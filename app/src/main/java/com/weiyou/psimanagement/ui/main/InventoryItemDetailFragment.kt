package com.weiyou.psimanagement.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import com.weiyou.psimanagement.PSIManagamentApplication
import com.weiyou.psimanagement.R
import com.weiyou.psimanagement.data.InventoryItem
import com.weiyou.psimanagement.databinding.FragmentInventoryItemDetailBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

/**
 * [InventoryItemDetailFragment] displays the details of the selected item.
 */
class InventoryItemDetailFragment : Fragment() {
    //    private val navigationArgs: ItemDetailFragmentArgs by navArgs()
    lateinit var inventoryItem: InventoryItem

    private val viewModel: MainViewModel by activityViewModels {
        MainViewModelFactory(
                (activity?.application as PSIManagamentApplication).inventoryItemDatabase.inventoryItemDao(),
                (activity?.application as PSIManagamentApplication).salesItemDatabase.salesItemDao(),
                (activity?.application as PSIManagamentApplication).purchaseItemDatabase.purchaseItemDao(),
                (activity?.application as PSIManagamentApplication).scrapItemDatabase.scrapItemDao()
        )
    }

    private var _binding: FragmentInventoryItemDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInventoryItemDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Binds views with the passed in item data.
     */
    private fun bind(inventoryItem: InventoryItem) {
        binding.itemName.text = inventoryItem.inventoryItemName
        binding.itemPrice.text = inventoryItem.inventoryItemPrice.toString()
        binding.itemCount.text = inventoryItem.inventoryItemQuantityInStock.toString()
//        binding.sellItem.isEnabled = viewModel.isStockAvailable(item)
        binding.sellItem.setOnClickListener { viewModel.sell1Item(inventoryItem) }
        binding.deleteItem.setOnClickListener { showConfirmationDialog() }
//        binding.editItem.setOnClickListener { editInventoryItem() }
        binding.btSellItems.setOnClickListener { viewModel.sellMoreItem(inventoryItem, Integer.parseInt(binding.etSaleAmount.text.toString())) }
        binding.btScrapItems.setOnClickListener { viewModel.scrapMoreItem(inventoryItem, Integer.parseInt(binding.etScrapAmount.text.toString())) }
        if(activity?.getTitle()?.toString()==getString(R.string.title_select_sales_items)){
            binding.llScrapItems.visibility=View.GONE
            binding.deleteItem.visibility=View.GONE
        }else if(activity?.getTitle()?.toString()==getString(R.string.title_select_scrap_items)){
            binding.llSaleItems.visibility=View.GONE
            binding.sellItem.visibility=View.GONE
        }
    }

    /**
     * Navigate to the Edit item screen.
     */
    private fun editInventoryItem() {
//        val action = ItemDetailFragmentDirections.actionItemDetailFragmentToAddItemFragment(
//            getString(R.string.edit_fragment_title),
//            item.id
//        )
//        this.findNavController().navigate(action)

        val EditItemFragment = EditInventoryItemFragment()
        val bundle = Bundle()
        bundle.putString("position", inventoryItem.inventoryItemId.toString())
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
        viewModel.deleteItem(inventoryItem)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = requireArguments().getString("position")
//        val id = navigationArgs.itemId
        // Retrieve the item details using the itemId.
        // Attach an observer on the data (instead of polling for changes) and only update the
        // the UI when the data actually changes.
        Log.d("IANIAN","InventoryItemDetailFragment113 id:"+id);
        viewModel.retrieveItem(Integer.parseInt(id)).observe(this.viewLifecycleOwner) { selectedItem ->
            inventoryItem = selectedItem
            bind(inventoryItem)
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
