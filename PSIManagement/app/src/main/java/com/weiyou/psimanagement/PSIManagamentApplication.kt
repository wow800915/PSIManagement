package com.weiyou.psimanagement

import android.app.Application
import com.weiyou.psimanagement.data.InventoryItemRoomDatabase
import com.weiyou.psimanagement.data.PurchaseItemRoomDatabase
import com.weiyou.psimanagement.data.SalesItemRoomDatabase
import com.weiyou.psimanagement.data.ScrapItemRoomDatabase

//20210906這邊room開始有改
class PSIManagamentApplication : Application() {
    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val inventoryItemDatabase: InventoryItemRoomDatabase by lazy { InventoryItemRoomDatabase.getDatabase(this) }
    val salesItemDatabase: SalesItemRoomDatabase by lazy { SalesItemRoomDatabase.getDatabase(this) }
    val purchaseItemDatabase: PurchaseItemRoomDatabase by lazy { PurchaseItemRoomDatabase.getDatabase(this) }
    val scrapItemDatabase: ScrapItemRoomDatabase by lazy { ScrapItemRoomDatabase.getDatabase(this) }
}
