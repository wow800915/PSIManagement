package com.weiyou.psimanagement.data


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

/**
 * Database class with a singleton INSTANCE object.
 */
@Database(entities = [ScrapItem::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class ScrapItemRoomDatabase : RoomDatabase() {
    //20210906這邊room開始有改,主要是下面這行的問題
    abstract fun scrapItemDao(): ScrapItemDao

    companion object {
        @Volatile
        private var INSTANCE: ScrapItemRoomDatabase? = null

        fun getDatabase(context: Context): ScrapItemRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        ScrapItemRoomDatabase::class.java,
                        "scrap_item_database"
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
