package com.example.psimanagement.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.NumberFormat

/**
 * Entity data class represents a single row in the database.
 */
@Entity
data class Sales(
    @PrimaryKey(autoGenerate = true)
    val salesItemId: Int = 0,
    @ColumnInfo(name = "barcode")
    val salesItemBarcode: Int,
    @ColumnInfo(name = "name")
    val salesItemName: String,
    //20210906這邊room開始有改
    @ColumnInfo(name = "price")
    val salesItemPrice: Double,
    @ColumnInfo(name = "quantity")
    val salesItemQuantityInStock: Int,
    @ColumnInfo(name = "time")
    val salesItemTime: Long,
    @ColumnInfo(name = "other")
    val salesItemOther: String,
)
/**
 * Returns the passed in price in currency format.
 */
//fun Item.getFormattedPrice(): String =
//        NumberFormat.getCurrencyInstance().format(itemPrice)