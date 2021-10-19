package com.example.psimanagement.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.NumberFormat

/**
 * Entity data class represents a single row in the database.
 */
@Entity
data class ScrapItem(
    @PrimaryKey(autoGenerate = true)
    val scrapItemId: Int = 0,
    @ColumnInfo(name = "barcode")
    val scrapItemBarcode: Int,
    @ColumnInfo(name = "name")
    val scrapItemName: String,
    //20210906這邊room開始有改
    @ColumnInfo(name = "price")
    val scrapItemPrice: Double,
    @ColumnInfo(name = "quantity")
    val scrapItemQuantityInStock: Int,
    @ColumnInfo(name = "time")
    val scrapItemTime: Long,
    @ColumnInfo(name = "other")
    val scrapItemOther: String,
)
/**
 * Returns the passed in price in currency format.
 */
//fun Item.getFormattedPrice(): String =
//        NumberFormat.getCurrencyInstance().format(itemPrice)