package com.weiyou.psimanagement.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


@Entity
data class PurchaseItem(
        @PrimaryKey(autoGenerate = true)
        val purchaseItemId: Int = 0,
        @ColumnInfo(name = "order")
        val purchaseItemOrder: String,
        @ColumnInfo(name = "barcode")
        val purchaseItemBarcode: String,
        @ColumnInfo(name = "name")
        val purchaseItemName: String,
        @ColumnInfo(name = "currency")
        val purchaseItemCurrency: String,
        //20210906這邊room開始有改
        @ColumnInfo(name = "price")
        val purchaseItemPrice: Double,
        @ColumnInfo(name = "quantity")
        val purchaseItemQuantityInStock: Int,
        @ColumnInfo(name = "date")
        val purchaseItemDate: Date,
        @ColumnInfo(name = "time")
        val purchaseItemTime: String,
        @ColumnInfo(name = "other")
        val purchaseItemOther: String,
)