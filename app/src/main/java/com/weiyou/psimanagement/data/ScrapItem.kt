package com.weiyou.psimanagement.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

/**
 * Entity data class represents a single row in the database.
 */
@Entity
data class ScrapItem(
        @PrimaryKey(autoGenerate = true)
        val scrapItemId: Int = 0,
        @ColumnInfo(name = "order")
        val scrapItemOrder: String,
        @ColumnInfo(name = "barcode")
        val scrapItemBarcode: String,
        @ColumnInfo(name = "name")
        val scrapItemName: String,
        //20210906這邊room開始有改
        @ColumnInfo(name = "currency")
        val scrapItemCurrency: String,
        @ColumnInfo(name = "price")
        val scrapItemPrice: Double,
        @ColumnInfo(name = "quantity")
        val scrapItemQuantityInStock: Int,
        @ColumnInfo(name = "date")
        val scrapItemDate: Date,
        @ColumnInfo(name = "time")
        val scrapItemTime: String,
        @ColumnInfo(name = "other")
        val scrapItemOther: String,
)
/**
 * Returns the passed in price in currency format.
 */
//fun Item.getFormattedPrice(): String =
//        NumberFormat.getCurrencyInstance().format(itemPrice)