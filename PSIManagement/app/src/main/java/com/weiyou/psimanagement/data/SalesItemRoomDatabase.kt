package com.weiyou.psimanagement.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

/**
 * Database class with a singleton INSTANCE object.
 */
@Database(entities = [SalesItem::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class SalesItemRoomDatabase : RoomDatabase() {
    //20210906這邊room開始有改,主要是下面這行的問題
    abstract fun salesItemDao(): SalesItemDao

    companion object {
        @Volatile
        private var INSTANCE: SalesItemRoomDatabase? = null

        fun getDatabase(context: Context): SalesItemRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        SalesItemRoomDatabase::class.java,
                        "sales_item_database"
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
