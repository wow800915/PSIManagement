package com.example.psimanagement.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Database class with a singleton INSTANCE object.
 */
@Database(entities = [Sales::class], version = 1, exportSchema = false)
abstract class SalesRoomDatabase : RoomDatabase() {
    //20210906這邊room開始有改,主要是下面這行的問題
    abstract fun salesDao(): SalesDao

    companion object {
        @Volatile
        private var INSTANCE: SalesRoomDatabase? = null

        fun getDatabase(context: Context): SalesRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SalesRoomDatabase::class.java,
                    "sales_database"
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
