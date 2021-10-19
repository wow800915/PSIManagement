package com.example.psimanagement.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.NumberFormat


@Entity
data class PurchaseItem(
    @PrimaryKey(autoGenerate = true)
    val purchaseItemId: Int = 0,
    @ColumnInfo(name = "barcode")
    val purchaseItemBarcode: Int,
    @ColumnInfo(name = "name")
    val purchaseItemName: String,
    //20210906這邊room開始有改
    @ColumnInfo(name = "price")
    val purchaseItemPrice: Double,
    @ColumnInfo(name = "quantity")
    val purchaseItemQuantityInStock: Int,
    @ColumnInfo(name = "time")
    val purchaseItemTime: Long,
    @ColumnInfo(name = "other")
    val purchaseItemOther: String,
)