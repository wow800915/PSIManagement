package com.weiyou.psimanagement.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.weiyou.psimanagement.data.ScrapItem
import com.weiyou.psimanagement.databinding.ItemListScrapItemBinding
import java.text.SimpleDateFormat
import java.util.*

class ScrapItemListAdapter(private val onItemClicked: (ScrapItem) -> Unit) :
        ListAdapter<ScrapItem, ScrapItemListAdapter.ItemViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
                ItemListScrapItemBinding.inflate(
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

    class ItemViewHolder(private var binding: ItemListScrapItemBinding) :
            RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ScrapItem) {
//            binding.itemName.text = item.inventoryItemName
//            binding.itemPrice.text = item.inventoryItemPrice.toString()
            binding.tvGetAmt.text = item.scrapItemQuantityInStock.toString()
//            val dateStr: String = fromLongToDate("yyyy-MM-dd HH:mm:ss", 1568020783663L)
            binding.tvSrrn.text = item.scrapItemName
            binding.tvAmt.text = item.scrapItemPrice.toString()
            binding.tvDateTime.text = SimpleDateFormat("yyyy-MM-dd").format(Date(item.scrapItemDate.time))
//            binding.tvDateTime.text = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date(item.purchaseItemDate.time))
        }
    }



    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<ScrapItem>() {
            override fun areItemsTheSame(oldItem: ScrapItem, newItem: ScrapItem): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: ScrapItem, newItem: ScrapItem): Boolean {
                return oldItem.scrapItemName == newItem.scrapItemName
            }
        }
    }

}
