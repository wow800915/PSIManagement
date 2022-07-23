package com.weiyou.psimanagement.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity data class represents a single row in the database.
 */
@Entity
data class InventoryItem(
        @PrimaryKey(autoGenerate = true)
        val inventoryItemId: Int = 0,
        @ColumnInfo(name = "order")
        val inventoryItemOrder: String,
        @ColumnInfo(name = "barcode")
        val inventoryItemBarcode: String,
        @ColumnInfo(name = "name")
        val inventoryItemName: String,
        //20210906這邊room開始有改
        @ColumnInfo(name = "currency")
        val inventoryItemCurrency: String,
        @ColumnInfo(name = "price")
        val inventoryItemPrice: Double,
        @ColumnInfo(name = "quantity")
        val inventoryItemQuantityInStock: Int,
        @ColumnInfo(name = "date")
        val inventoryItemDate: String,
        @ColumnInfo(name = "time")
        val inventoryItemTime: String,
        @ColumnInfo(name = "other")
        val inventoryItemOther: String,
)
/**
 * Returns the passed in price in currency format.
 */
//fun Item.getFormattedPrice(): String =
//        NumberFormat.getCurrencyInstance().format(itemPrice)