package com.example.psimanagement.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.psimanagement.data.InventoryItem
import com.example.psimanagement.data.PurchaseItem
import com.example.psimanagement.databinding.ItemListPurchaseItemBinding

class PurchaseItemListAdapter(private val onItemClicked: (PurchaseItem) -> Unit) :
    ListAdapter<PurchaseItem, PurchaseItemListAdapter.ItemViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemListPurchaseItemBinding.inflate(
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

    class ItemViewHolder(private var binding: ItemListPurchaseItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PurchaseItem) {
//            binding.itemName.text = item.inventoryItemName
//            binding.itemPrice.text = item.inventoryItemPrice.toString()
//            binding.itemQuantity.text = item.inventoryItemQuantityInStock.toString()
            binding.tvSrrn.text = item.purchaseItemName
            binding.tvAmt.text = item.purchaseItemPrice.toString()
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<PurchaseItem>() {
            override fun areItemsTheSame(oldItem: PurchaseItem, newItem: PurchaseItem): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: PurchaseItem, newItem: PurchaseItem): Boolean {
                return oldItem.purchaseItemName == newItem.purchaseItemName
            }
        }
    }

}
