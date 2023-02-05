package com.weiyou.psimanagement.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.weiyou.psimanagement.data.PurchaseItem
import com.weiyou.psimanagement.databinding.ItemListPurchaseItemBinding
import java.text.SimpleDateFormat
import java.util.*

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
//            val dateStr: String = fromLongToDate("yyyy-MM-dd HH:mm:ss", 1568020783663L)
            binding.tvSrrn.text = item.purchaseItemName
            binding.tvAmt.text = item.purchaseItemPrice.toString()
            binding.tvDateTime.text = SimpleDateFormat("yyyy-MM-dd").format(Date(item.purchaseItemDate.time))
//            binding.tvDateTime.text = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date(item.purchaseItemDate.time))
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
