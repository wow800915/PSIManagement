package com.example.psimanagement

import android.app.Application
import com.example.psimanagement.data.Inventory
import com.example.psimanagement.data.InventoryRoomDatabase
//20210906這邊room開始有改
class PSIManagamentApplication : Application() {
    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database: InventoryRoomDatabase by lazy { InventoryRoomDatabase.getDatabase(this) }
}
