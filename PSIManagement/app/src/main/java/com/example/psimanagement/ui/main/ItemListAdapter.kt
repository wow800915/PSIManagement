package com.example.psimanagement.ui.main


import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.psimanagement.data.Inventory
import com.example.psimanagement.databinding.ItemListItemBinding

/**
 * [ListAdapter] implementation for the recyclerview.
 */

class ItemListAdapter(private val onItemClicked: (Inventory) -> Unit) :
    ListAdapter<Inventory, ItemListAdapter.ItemViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemListItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val current = getItem(position)
//        holder.itemView.setOnClickListener {
//            onItemClicked(current)
//            Log.d("IANIAN","ItemListAdapter33");
//        }
        holder.bind(current)
    }

    class ItemViewHolder(private var binding: ItemListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Inventory) {
            binding.itemName.text = item.inventoryItemName
            binding.itemPrice.text = item.inventoryItemPrice.toString()
            binding.itemQuantity.text = item.inventoryItemQuantityInStock.toString()
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Inventory>() {
            override fun areItemsTheSame(oldItem: Inventory, newItem: Inventory): Boolean {
                Log.d("IANIAN","oldItem === newItem:"+(oldItem === newItem));
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Inventory, newItem: Inventory): Boolean {
                return oldItem.inventoryItemName == newItem.inventoryItemName
                Log.d("IANIAN","oldItem.inventoryItemName == newItem.inventoryItemName:"+(oldItem.inventoryItemName == newItem.inventoryItemName));
            }
        }
    }
}
