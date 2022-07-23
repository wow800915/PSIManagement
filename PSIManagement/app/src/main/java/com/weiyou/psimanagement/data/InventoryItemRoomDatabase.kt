package com.weiyou.psimanagement.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Database class with a singleton INSTANCE object.
 */
@Database(entities = [InventoryItem::class], version = 1, exportSchema = false)
abstract class InventoryItemRoomDatabase : RoomDatabase() {
    //20210906這邊room開始有改,主要是下面這行的問題
    abstract fun inventoryItemDao(): InventoryItemDao

    companion object {
        @Volatile
        private var INSTANCE: InventoryItemRoomDatabase? = null

        fun getDatabase(context: Context): InventoryItemRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        InventoryItemRoomDatabase::class.java,
                        "inventory_item_database"
                )
                        // Wipes and rebuilds instead of migrating if no Migration object.
                        // Migration is not part of this codelab.
                        .fallbackToDestructiveMigration()
                        .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}
