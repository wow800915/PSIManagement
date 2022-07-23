package com.weiyou.psimanagement.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.weiyou.psimanagement.data.SalesItem
import com.weiyou.psimanagement.databinding.ItemListSalesItemBinding
import java.text.SimpleDateFormat
import java.util.*

class SalesItemListAdapter(private val onItemClicked: (SalesItem) -> Unit) :
    ListAdapter<SalesItem, SalesItemListAdapter.ItemViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemListSalesItemBinding.inflate(
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

    class ItemViewHolder(private var binding: ItemListSalesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SalesItem) {
//            binding.itemName.text = item.inventoryItemName
//            binding.itemPrice.text = item.inventoryItemPrice.toString()
            binding.tvGetAmt.text = item.salesItemQuantityInStock.toString()
//            val dateStr: String = fromLongToDate("yyyy-MM-dd HH:mm:ss", 1568020783663L)
            binding.tvSrrn.text = item.salesItemName
            binding.tvAmt.text = item.salesItemPrice.toString()
            binding.tvDateTime.text = SimpleDateFormat("yyyy-MM-dd").format(Date(item.salesItemDate.time))
//            binding.tvDateTime.text = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date(item.purchaseItemDate.time))
        }
    }



    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<SalesItem>() {
            override fun areItemsTheSame(oldItem: SalesItem, newItem: SalesItem): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: SalesItem, newItem: SalesItem): Boolean {
                return oldItem.salesItemName == newItem.salesItemName
            }
        }
    }

}
