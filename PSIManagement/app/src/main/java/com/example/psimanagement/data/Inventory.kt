package com.example.psimanagement.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.NumberFormat

/**
 * Entity data class represents a single row in the database.
 */
@Entity
data class Inventory(
    @PrimaryKey(autoGenerate = true)
    val inventoryItemId: Int = 0,
    @ColumnInfo(name = "barcode")
    val inventoryItemBarcode: Int,
    @ColumnInfo(name = "name")
    val inventoryItemName: String,
    //20210906這邊room開始有改
    @ColumnInfo(name = "price")
    val inventoryItemPrice: Double,
    @ColumnInfo(name = "quantity")
    val inventoryItemQuantityInStock: Int,
    @ColumnInfo(name = "time")
    val inventoryItemTime: Long,
    @ColumnInfo(name = "other")
    val inventoryItemOther: String,
)
/**
 * Returns the passed in price in currency format.
 */
//fun Item.getFormattedPrice(): String =
//        NumberFormat.getCurrencyInstance().format(itemPrice)