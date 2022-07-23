package com.weiyou.psimanagement.ui.main


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.weiyou.psimanagement.data.InventoryItem
import com.weiyou.psimanagement.databinding.ItemListInventoryItemBinding

/**
 * [ListAdapter] implementation for the recyclerview.
 */

class InventoryItemListAdapter(private val onItemClicked: (InventoryItem) -> Unit) :
        ListAdapter<InventoryItem, InventoryItemListAdapter.ItemViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
                ItemListInventoryItemBinding.inflate(
                        LayoutInflater.from(
                                parent.context
                        )
                )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClicked(current)
        }
        holder.bind(current)
    }

    class ItemViewHolder(private var binding: ItemListInventoryItemBinding) :
            RecyclerView.ViewHolder(binding.root) {
        fun bind(item: InventoryItem) {
            binding.itemName.text = item.inventoryItemName
            binding.itemPrice.text = item.inventoryItemPrice.toString()
            binding.itemQuantity.text = item.inventoryItemQuantityInStock.toString()
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<InventoryItem>() {
            override fun areItemsTheSame(oldItem: InventoryItem, newItem: InventoryItem): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: InventoryItem, newItem: InventoryItem): Boolean {
                return oldItem.inventoryItemName == newItem.inventoryItemName
            }
        }
    }
}
