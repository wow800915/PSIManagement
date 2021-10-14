package com.example.psimanagement

import android.app.Application
import com.example.psimanagement.data.*

//20210906這邊room開始有改
class PSIManagamentApplication : Application() {
    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val inventoryDatabase: InventoryRoomDatabase by lazy { InventoryRoomDatabase.getDatabase(this) }
    val salesDatabase: SalesRoomDatabase by lazy { SalesRoomDatabase.getDatabase(this) }
    val purchaseDatabase: PurchaseRoomDatabase by lazy { PurchaseRoomDatabase.getDatabase(this) }
    val scrapDatabase: ScrapRoomDatabase by lazy { ScrapRoomDatabase.getDatabase(this) }
}
