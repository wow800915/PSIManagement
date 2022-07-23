package com.weiyou.psimanagement.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import java.util.*

/**
 * Database access object to access the Inventory database
 */
@Dao
interface ScrapItemDao {
    //20210906這邊room開始有改
//這邊的NAME不知道要不要改成inventoryItemName
    @Query("SELECT * from scrapItem ORDER BY name ASC")
    fun getScrapItems(): Flow<List<ScrapItem>>

    //inventoryItemId不知道要不要改成ID
    @Query("SELECT * from scrapItem WHERE scrapItemId = :scrapItemId")
    fun getScrapItem(scrapItemId: Int): Flow<ScrapItem>

    @Query("SELECT * FROM scrapItem WHERE date BETWEEN :from AND :to")
    fun getCustomScrapItems(from: Date, to: Date): Flow<List<ScrapItem>>

    // Specify the conflict strategy as IGNORE, when the user tries to add an
    // existing Item into the database Room ignores the conflict.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(scrapItem: ScrapItem)

    @Update
    suspend fun update(scrapItem: ScrapItem)

    @Delete
    suspend fun delete(scrapItem: ScrapItem)
}