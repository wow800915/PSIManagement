package com.weiyou.psimanagement.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
//import java.sql.Date
import java.util.*

/**
 * Entity data class represents a single row in the database.
 */
@Entity
data class SalesItem(
        @PrimaryKey(autoGenerate = true)
        val salesItemId: Int = 0,
        @ColumnInfo(name = "order")
        val salesItemOrder: String,
        @ColumnInfo(name = "barcode")
        val salesItemBarcode: String,
        @ColumnInfo(name = "name")
        val salesItemName: String,
        //20210906這邊room開始有改
        @ColumnInfo(name = "currency")
        val salesItemCurrency: String,
        @ColumnInfo(name = "price")
        val salesItemPrice: Double,
        @ColumnInfo(name = "quantity")
        val salesItemQuantityInStock: Int,
        @ColumnInfo(name = "date")
        val salesItemDate: Date,
        @ColumnInfo(name = "time")
        val salesItemTime: String,
        @ColumnInfo(name = "other")
        val salesItemOther: String,
)
/**
 * Returns the passed in price in currency format.
 */
//fun Item.getFormattedPrice(): String =
//        NumberFormat.getCurrencyInstance().format(itemPrice)